package fr.projects.online_checkout.paypal.mapper.paypalToModel.impl;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Phone;
import fr.projects.online_checkout.core.constantes.Patterns;
import fr.projects.online_checkout.core.exceptions.*;
import fr.projects.online_checkout.core.model.*;
import fr.projects.online_checkout.core.utils.BigDecimalUtils;
import fr.projects.online_checkout.core.utils.PaiementCalculator;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.paypal.mapper.paypalToModel.PaiementMapper;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PaiementMapperImpl implements PaiementMapper {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************
  private ExceptionMessages exceptionMessages;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************

  public PaiementMapperImpl(ExceptionMessages exceptionMessages) {
    this.exceptionMessages = exceptionMessages;
  }


  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  @Override
  public Payment toPaypalPayment(@Valid Paiement paiement) throws PaypalMerchandEmailMissingException, ActeursPaiementMissingException, UnExpectedValueException, PanierMissingException, PaiementMotifMissingException {

    //Vérifier que les différents éléments obligatoires du paiement sont bien non null
    RequireObjects.requireNotNull(paiement.getPayeur(), ActeursPaiementMissingException.class, exceptionMessages.PAYEUR_NULL);
    RequireObjects.requireNotNull(paiement.getBeneficiaire(), ActeursPaiementMissingException.class, exceptionMessages.BENEFICIAIRE_NULL);
    RequireObjects.requireNotNull(paiement.getPanier(), PanierMissingException.class, exceptionMessages.PANIER_NULL);
    RequireObjects.requireNotNull(paiement.getMotif(), PaiementMotifMissingException.class, exceptionMessages.MOTIF_NULL);

    return new Payment()
            .setIntent(paiement.getMotif().getPaypalLibelle())
            .setPayee(extractPayee(paiement))
            .setPayer(extractPayer(paiement))
            .setTransactions(extractTransactions(paiement));
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************

  /**
   * Méthode permettant de construire la liste des transactions paypal à partir du paiement
   *
   * @param paiement
   * @return
   */
  private List<Transaction> extractTransactions(Paiement paiement) {
    //Initialiser une liste de transaction paypal
    List<Transaction> transactions = new ArrayList<>();

    //Calculer le total du panier du paiement
    BigDecimal totalPanier = PaiementCalculator.calculTotalPanier(paiement.getPanier());

    //Extraire les différents montants taxe, frais gestion, frais livraison du paiement
    BigDecimal montantTaxe = paiement.getMontantTaxe();
    final BigDecimal montantFraisGestion = paiement.getMontantFraisGestion();
    final BigDecimal montantLivraison = paiement.getMontantLivraison();
    final double pourcentageTaxe = paiement.getPourcentageTaxe();

    //Définir un objet Detail paypal qui représente le détail de l'objet Amount représentant le montant d'une transaction
    //Puis le remplir avec les différents montants du paiement
    final Details details = new Details()
      .setSubtotal(totalPanier.toPlainString());

    BigDecimal total = totalPanier;

    if (Objects.nonNull(montantTaxe)) {
      details.setTax(montantTaxe.toPlainString());
      total = BigDecimalUtils.add(total, montantTaxe);
    } else if (Objects.nonNull(pourcentageTaxe)) {
      montantTaxe = BigDecimalUtils.mutiply(totalPanier, BigDecimalUtils.create(String.valueOf(pourcentageTaxe / 100)));
      details.setTax(montantTaxe.toPlainString());
      total = BigDecimalUtils.add(total, montantTaxe);
    }

    if (Objects.nonNull(montantFraisGestion)) {
      details.setHandlingFee(montantFraisGestion.toPlainString());
      total = BigDecimalUtils.add(total, montantFraisGestion);
    }
    if (Objects.nonNull(montantLivraison)) {
      details.setShippingDiscount(montantLivraison.toPlainString());
      total = BigDecimalUtils.add(total, montantLivraison);
    }

    //Construire l'objet Amount paypal
    Amount amount = new Amount()
      .setCurrency(paiement.getDevisePaiement().name())
      .setTotal(total.toPlainString())
      .setDetails(details);

    //Construire l'objet transaction paypal
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription(paiement.getDescription());
    transaction.setItemList(extractItemList(paiement.getPanier()));

    //ajouter la transaction à la liste de transaction à retourner
    transactions.add(transaction);
    return transactions;
  }

  /**
   * Méthode permettant de constraire l'objet ItemList à partir d'un panier
   * @param panier
   * @return
   */
  private ItemList extractItemList(Panier panier) {
    //Initialiser une liste d'items
    List<Item> items = new ArrayList<>();

    //Pour chaque ligne de commande du panier
    panier.getLigneCommandes().forEach(ligne -> {
      //Extraire l'article et le nombre d'articles
      Article article = ligne.getArticle();
      int nbre = ligne.getNombreArticle();

      //Construire un item paypal
      Item item = new Item()
        .setCategory(article.getCategorie())
        .setCurrency((Objects.nonNull(article.getDevise())) ? article.getDevise().name() : Devise.EUR.name())
        .setDescription(article.getDescription())
        .setName(article.getNom())
        .setPrice(article.getPrix().toPlainString())
        .setQuantity(String.valueOf(nbre));

      //puis l'ajouter à la liste d'items
      items.add(item);
    });

    //Retourner un objet ItemList paypal
    return new ItemList()
      .setItems(items);
  }

  /**
   * Méthode permettant de construire l'objet Payer de paypal à parir d'un paiement
   *
   * @param paiement
   * @return
   * @throws ActeursPaiementMissingException
   * @throws UnExpectedValueException
   */
  private Payer extractPayer(Paiement paiement) throws ActeursPaiementMissingException, UnExpectedValueException {
    //Extraire le payeur
    Payeur payeur = paiement.getPayeur();

    //Vérifier que le payeur est bien renseigné
    RequireObjects.requireNotNull(payeur, ActeursPaiementMissingException.class, exceptionMessages.PAYEUR_NULL);

    //Extraire la méthode de paiement
    final PaiementMethods methodeDePaiement = paiement.getMethodeDePaiement();

    //si le mode de paiement est non null extraire libelle
    String libelle = null;
    if (Objects.nonNull(methodeDePaiement)) libelle = methodeDePaiement.getLibelle();

    //Vérifier que le mode de paiement préciser est bien le mode paypal
    RequireObjects.expect(libelle, PaiementMethods.PAYPAL.getLibelle(), UnExpectedValueException.class, exceptionMessages.MODE_PAIEMENT_INCORRECT);

    //Extraire les propriétés du payeur
    Identite identite = payeur.getIdentite();
    Coordonnees coordonnees = payeur.getCoordonnees();
    IdentifiantsPaypal identifiantsPaypal = payeur.getIdentifiantsPaypal();

    //Si l'identité est non null, extraire la date de naissace, le nom et le prénom
    LocalDate dateNaissance = null;
    String nom = null;
    String prenom = null;
    if (Objects.nonNull(identite)) {
      dateNaissance = identite.getDateNaissance();
      nom = identite.getNom();
      prenom = identite.getPrenom();
    }

    //Si la date de naissace est non null la formatter
    String formattedDateNaissance = null;
    if (Objects.nonNull(dateNaissance))
      formattedDateNaissance = dateNaissance.format(DateTimeFormatter.ofPattern(Patterns.DATE_NAISSANCE));

    //Extraire le payerID
    String id = null;
    if (Objects.nonNull(identifiantsPaypal)) id = identifiantsPaypal.getId();

    //Si les coordonnées est non null, extraire l'adresse, l'email et le phone
    Adresse adresse = null;
    fr.projects.online_checkout.core.model.Phone phone = null;
    String email = null;
    if (Objects.nonNull(coordonnees)) {
      adresse = coordonnees.getAdresse();
      phone = coordonnees.getPhone();
      email = coordonnees.getEmail();
    }

    //Construire d'adresse Paypal avec les propriétés de l'adresse du payeur
    Address address = new Address();
    if (Objects.nonNull(adresse)) {
      address.setLine1(adresse.getNumero())
        .setLine2(adresse.getRue())
        .setPostalCode(adresse.getCodePostal())
        .setCity(adresse.getVille())
        .setCountryCode(adresse.getPays());
    }

    //Si le phone est non null, extraire l'indicatif et le numero de téléphone
    String indicatif = null;
    String numero = null;
    if (Objects.nonNull(phone)) {
      indicatif = phone.getIndicatif();
      numero = phone.getNumero();
    }

    //Retourner le Payer paypal construit avec les propriétés du payeur
    return new Payer()
      .setPaymentMethod(libelle)
      .setPayerInfo(new PayerInfo()
        .setPayerId(id)
        .setBillingAddress(address)
        .setBirthDate(formattedDateNaissance)
        .setEmail(email)
        .setFirstName(prenom)
        .setLastName(nom)
        .setPhone("(" + indicatif + ")" + numero));
  }

  /**
   * Méthode permettant de construire un Payee paypal à partir d'un paiement
   *
   * @param paiement
   * @return
   * @throws PaypalMerchandEmailMissingException
   */
  private Payee extractPayee(Paiement paiement) throws PaypalMerchandEmailMissingException, ActeursPaiementMissingException {
    //Extraire le beneficiaire
    Beneficiaire beneficiaire = paiement.getBeneficiaire();

    //Vérifier que le bénéficiaire est bien renseigné
    RequireObjects.requireNotNull(beneficiaire, ActeursPaiementMissingException.class, exceptionMessages.BENEFICIAIRE_NULL);

    //Extraire les différentes propriétes d'une Personne
    Identite identite = beneficiaire.getIdentite();
    Coordonnees coordonnees = beneficiaire.getCoordonnees();
    IdentifiantsPaypal identifiantsPaypal = beneficiaire.getIdentifiantsPaypal();

    //Si les coordonnées sont non null extraire le phone
    fr.projects.online_checkout.core.model.Phone phone = null;
    if (Objects.nonNull(coordonnees)) {
      phone = coordonnees.getPhone();
    }

    String email = null;
    String nom = null;
    String prenom = null;
    String indicatif = null;
    String numero = null;

    //Si les identifiants paypal sont non null extraire le mail du bénéficaire
    if (Objects.nonNull(identifiantsPaypal)) email = identifiantsPaypal.getEmail();

    //Vérifier que l'email du bénéficiaire existe bien
    RequireObjects.requireNotNull(email, PaypalMerchandEmailMissingException.class, exceptionMessages.PAYPAL_MERCHAND_EMAIL_NULL);

    //Si l'identité est non null extraire les propriétes non et prenom
    if (Objects.nonNull(identite)) {
      prenom = identite.getPrenom();
      nom = identite.getNom();
    }

    //Si phone est non null extraire les propriétes indicatif et numero
    if (Objects.nonNull(phone)) {
      indicatif = phone.getIndicatif();
      numero = phone.getNumero();
    }

    //Retouner un Payee paypal avec les propriétes extraites du bénéficiaire
    return new Payee()
      .setEmail(email)
      .setFirstName(prenom)
      .setLastName(nom)
            .setPhone(new Phone()
              .setCountryCode(indicatif)
              .setNationalNumber(numero));
  }
}

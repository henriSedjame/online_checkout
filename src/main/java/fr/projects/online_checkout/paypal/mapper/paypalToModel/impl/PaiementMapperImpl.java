package fr.projects.online_checkout.paypal.mapper.paypalToModel.impl;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Phone;
import fr.projects.online_checkout.core.exceptions.ExceptionMessages;
import fr.projects.online_checkout.core.exceptions.PaypalMerchandEmailMissingException;
import fr.projects.online_checkout.core.model.*;
import fr.projects.online_checkout.core.utils.BigDecimalUtils;
import fr.projects.online_checkout.core.utils.PaiementCalculator;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.paypal.mapper.paypalToModel.PaiementMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
  public Payment toPaypalPayment(Paiement paiement) throws PaypalMerchandEmailMissingException {
    return new Payment()
            .setIntent(paiement.getMotif().getPaypalLibelle())
            .setPayee(extractPayee(paiement))
            .setPayer(extractPayer(paiement))
            .setTransactions(extractTransactions(paiement));
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************

  private List<Transaction> extractTransactions(Paiement paiement) {

    List<Transaction> transactions = new ArrayList<>();

    BigDecimal totalPanier = PaiementCalculator.calculTotalPanier(paiement.getPanier());

    BigDecimal montantTaxe = paiement.getMontantTaxe();
    final BigDecimal montantFraisGestion = paiement.getMontantFraisGestion();
    final BigDecimal montantLivraison = paiement.getMontantLivraison();
    final double pourcentageTaxe = paiement.getPourcentageTaxe();

    BigDecimal total = totalPanier;

    final Details details = new Details()
      .setSubtotal(totalPanier.toPlainString());

    if (!Objects.isNull(montantTaxe)) {
      details.setTax(montantTaxe.toPlainString());
      total = BigDecimalUtils.add(total, montantTaxe);
    } else if (!Objects.isNull(pourcentageTaxe)) {
      montantTaxe = BigDecimalUtils.mutiply(totalPanier, BigDecimalUtils.create(String.valueOf(pourcentageTaxe / 100)));
      details.setTax(montantTaxe.toPlainString());
      total = BigDecimalUtils.add(total, montantTaxe);
    }

    if (!Objects.isNull(montantFraisGestion)) {
      details.setHandlingFee(montantFraisGestion.toPlainString());
      total = BigDecimalUtils.add(total, montantFraisGestion);
    }
    if (!Objects.isNull(montantLivraison)) {
      details.setShippingDiscount(montantLivraison.toPlainString());
      total = BigDecimalUtils.add(total, montantLivraison);
    }

    Amount amount = new Amount()
      .setCurrency(paiement.getDevisePaiement().name())
      .setTotal(total.toPlainString())
      .setDetails(details);

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription(paiement.getDescription());
    transaction.setItemList(extractItemList(paiement.getPanier()));

    transactions.add(transaction);
    return transactions;
  }

  private ItemList extractItemList(Panier panier) {
    List<Item> items = new ArrayList<>();

    panier.getLigneCommandes().forEach(ligne -> {
      Article article = ligne.getArticle();
      int nbre = ligne.getNombreArticle();

      Item item = new Item()
              .setCategory(article.getCategorie())
              .setCurrency(article.getDevise().name())
              .setDescription(article.getDescription())
              .setName(article.getNom())
        .setPrice(article.getPrix().toPlainString())
              .setQuantity(String.valueOf(nbre));

      items.add(item);
    });
    return new ItemList()
            .setItems(items);
  }

  private Payer extractPayer(Paiement paiement) {
    Payeur payeur = paiement.getPayeur();
    Identite identite = payeur.getIdentite();
    Coordonnees coordonnees = payeur.getCoordonnees();
    Adresse adresse = coordonnees.getAdresse();

    Address address = new Address();
    address.setLine1(adresse.getNumero())
            .setLine2(adresse.getRue())
            .setPostalCode(adresse.getCodePostal())
            .setCity(adresse.getVille())
            .setCountryCode(adresse.getPays());

    String indicatif = coordonnees.getPhone().getIndicatif();
    String numero = coordonnees.getPhone().getNumero();

    return new Payer()
            .setPaymentMethod(paiement.getMethodeDePaiement().getLibelle())
            .setPayerInfo(new PayerInfo()
              .setPayerId((payeur.getIdentifiantsPaypal() != null) ? payeur.getIdentifiantsPaypal().getId() : null)
                    .setBillingAddress(address)
                    .setBirthDate(identite.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .setEmail(coordonnees.getEmail())
                    .setFirstName(identite.getPrenom())
                    .setLastName(identite.getNom())
                    .setPhone("(" + indicatif + ")" + numero));
  }

  /**
   * Méthode permettant de construire un Payee paypal à partir d'un paiement
   *
   * @param paiement
   * @return
   * @throws PaypalMerchandEmailMissingException
   */
  private Payee extractPayee(Paiement paiement) throws PaypalMerchandEmailMissingException {
    //Extraire le beneficiaire
    Beneficiaire beneficiaire = paiement.getBeneficiaire();

    //Extraire les différentes propriétes d'une Personne
    Identite identite = null;
    Coordonnees coordonnees = null;
    IdentifiantsPaypal identifiantsPaypal = null;

    //Si le beneficiaire est non null extraire ses propriétés
    if (Objects.nonNull(beneficiaire)) {
      identite = beneficiaire.getIdentite();
      coordonnees = beneficiaire.getCoordonnees();
      identifiantsPaypal = beneficiaire.getIdentifiantsPaypal();
    }

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

package fr.projects.online_checkout.paypal.mapper.paypalToModel.impl;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Phone;
import fr.projects.online_checkout.core.model.*;
import fr.projects.online_checkout.paypal.mapper.paypalToModel.PaiementMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PaimentMapperImpl implements PaiementMapper {

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  @Override
  public Payment toPaypalPayment(Paiement paiement) {

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

    DetailPaiement detailPaiement = paiement.getDetailPaiement();

    Transaction transaction = new Transaction();
    transaction.setAmount(extractAmount(detailPaiement));
    transaction.setDescription(detailPaiement.getDescription());
    transaction.setItemList(extractItemList(paiement.getPanier()));

    transactions.add(transaction);
    return transactions;
  }

  private Amount extractAmount(DetailPaiement detailPaiement) {
    return new Amount()
            .setCurrency(detailPaiement.getDevisePaiement().name())
            .setTotal(detailPaiement.getMontantTTC().toPlainString())
            .setDetails(new Details()
                    .setSubtotal(detailPaiement.getMontantHT().toPlainString())
                    .setTax(detailPaiement.getMontantTaxe().toPlainString())
                    .setHandlingFee(detailPaiement.getMontantFraisGestion().toPlainString())
                    .setShippingDiscount(detailPaiement.getMontantLivraison().toPlainString()));
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
              .setPrice(article.getPrixHT().toPlainString())
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
    fr.projects.online_checkout.core.model.Phone phone = coordonnees.getPhone();

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
                    .setPayerId(payeur.getPaypalPayerID())
                    .setBillingAddress(address)
                    .setBirthDate(identite.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .setEmail(coordonnees.getEmail())
                    .setFirstName(identite.getPrenom())
                    .setLastName(identite.getNom())
                    .setPhone("(" + indicatif + ")" + numero));
  }

  private Payee extractPayee(Paiement paiement) {
    Beneficiaire beneficiaire = paiement.getBeneficiaire();
    Identite identite = beneficiaire.getIdentite();
    Coordonnees coordonnees = beneficiaire.getCoordonnees();
    fr.projects.online_checkout.core.model.Phone phone = coordonnees.getPhone();
    return new Payee()
            .setEmail(coordonnees.getEmail())
            .setFirstName(identite.getPrenom())
            .setLastName(identite.getNom())
            .setPhone(new Phone()
                    .setCountryCode(phone.getIndicatif())
                    .setNationalNumber(phone.getNumero()));
  }
}

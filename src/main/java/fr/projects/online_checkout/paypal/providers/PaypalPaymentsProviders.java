package fr.projects.online_checkout.paypal.providers;

import com.paypal.api.payments.*;
import fr.projects.online_checkout.paypal.constants.PaypalIntents;
import fr.projects.online_checkout.paypal.constants.PaypalPaymentMethods;
import fr.projects.online_checkout.paypal.controller.Routes;
import fr.projects.online_checkout.paypal.exceptions.UnstantiableClassException;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
@Profile("dev")
public class PaypalPaymentsProviders {

  private PaypalPaymentsProviders() throws UnstantiableClassException {
    throw new UnstantiableClassException("Cette classe n'est pas instantibale");
  }

  /**
   * Methode de création d'un paiement par paypal
   * @return
   */
  public static Payment createAPayment() {

    /**
     *  Créer un montant
     * ajouter la devise
     * ajouter le total
     */
    Amount amount = new Amount();
    amount.setCurrency("EUR");
    amount.setTotal("1.00");

    /**
     * Créer une transaction
     * Mettre la transaction dans une liste de transactions
     *
     */
    List<Transaction> transactions = new ArrayList<>();
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription("Transaction de test");
    transactions.add(transaction);

    /**
     * Créer un payeur
     */
    Payer payer = new Payer();
    PayerInfo payerInfo = new PayerInfo();
    payer.setPaymentMethod(PaypalPaymentMethods.PAYPAL.getLibelle());
    payerInfo.setEmail("sedhjodev-buyer@gmail.com");
    payerInfo.setPayerId("ERTYF");
    payer.setPayerInfo(payerInfo);


    /**
     * Créer les urls de redirection
     * 1 - en cas d'annulation
     * 2 - en cas deretour après paiement
     */
    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl(Routes.HOST + Routes.CANCEL);
    redirectUrls.setReturnUrl(Routes.HOST + Routes.EXECUTE);

    /**
     * Créer un paiement
     */
    Payment payment = new Payment();
    payment.setIntent(PaypalIntents.SALE.getLibelle());
    payment.setPayer(payer);
    payment.setTransactions(transactions);
    payment.setRedirectUrls(redirectUrls);
    return payment;

  }

  public static Payment createAuthorizationPayment(){

    Payer payer = new Payer()
      .setPaymentMethod("paypal");

    RedirectUrls redirectUrls = new RedirectUrls()
      .setCancelUrl(Routes.HOST + Routes.CANCEL)
      .setReturnUrl(Routes.HOST + Routes.AUTHORIZE );

    Details details = new Details()
      .setShipping("0.5")
      .setSubtotal("4")
      .setTax("0.05");

    Amount amount = new Amount()
      .setCurrency("EUR")
      .setTotal("4.55") // somme des montants du details
      .setDetails(details);

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription("This is a authorization payment transaction description");

    List<Transaction> transactions = new ArrayList<>();
    transactions.add(transaction);

    return new Payment()
      .setIntent(PaypalIntents.AUTHORIZE.getLibelle())
      .setPayer(payer)
      .setTransactions(transactions)
      .setRedirectUrls(redirectUrls);
  }

  public static Payment createOrderPayment(){

    Payer payer = new Payer()
      .setPaymentMethod("paypal");

    RedirectUrls redirectUrls = new RedirectUrls()
      .setCancelUrl(Routes.HOST + Routes.CANCEL)
      .setReturnUrl(Routes.HOST+ Routes.ORDER);

    Details details = new Details()
      .setShipping("0.5")
      .setSubtotal("4")
      .setTax("0.05");

    Amount amount = new Amount()
      .setCurrency("EUR")
      .setTotal("4.55") // somme des montants du details
      .setDetails(details);

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription("This is a order payment transaction description");

    List<Transaction> transactions = new ArrayList<>();
    transactions.add(transaction);

    return new Payment()
      .setIntent(PaypalIntents.ORDER.getLibelle())
      .setPayer(payer)
      .setTransactions(transactions)
      .setRedirectUrls(redirectUrls);

  }
}

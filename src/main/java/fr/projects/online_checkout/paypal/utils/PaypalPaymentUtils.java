package fr.projects.online_checkout.paypal.utils;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.paypal.constants.PaypalConstants;
import fr.projects.online_checkout.paypal.exceptions.UnstantiableClassException;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
public class PaypalPaymentUtils {

  private PaypalPaymentUtils() throws UnstantiableClassException {
    throw new UnstantiableClassException("Cette classe n'est pas instantibale");
  }

  /**
   * Méthode permettant de récupérer l'url d'approbation d'un paiement paypal
   * @param payment
   * @return
   */
  public static String getApprovalUrl(Payment payment){
    return payment.getLinks()
      .stream()
      .filter(link -> link.getRel().equals(PaypalConstants.APPROVAL_URL))
      .map(Links::getHref)
      .findFirst()
      .orElse(null);
  }
}

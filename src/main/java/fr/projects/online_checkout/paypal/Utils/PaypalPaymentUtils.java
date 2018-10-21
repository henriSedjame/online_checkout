package fr.projects.online_checkout.paypal.Utils;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.paypal.configuration.PaypalConstants;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
public class PaypalPaymentUtils {

  public static String getApprovalUrl(Payment payment){
    return payment.getLinks()
      .stream()
      .filter(link -> link.getRel().equals(PaypalConstants.APPROVAL_URL))
      .map(link -> link.getHref())
      .findFirst()
      .orElse(null);
  }
}

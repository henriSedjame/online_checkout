package fr.projects.online_checkout.paypal.constants;

import fr.projects.online_checkout.paypal.exceptions.UnstantiableClassException;

public class PaypalConstants {

  public static final String APPROVED = "approved";

  public static final String APPROVAL_URL = "approval_url";
  public static final String PAYMENT_ID = "paymentId";

  private PaypalConstants() throws UnstantiableClassException {
    throw new UnstantiableClassException("Cette classe n'est pas instantibale");
  }
  public static final String PAYER_ID = "PayerID";
}

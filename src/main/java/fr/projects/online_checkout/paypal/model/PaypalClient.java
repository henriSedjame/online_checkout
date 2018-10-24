package fr.projects.online_checkout.paypal.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 24/10/2018
 * @Class purposes : .......
 */
@Data
@Builder
public class PaypalClient {
  private String clientId;
  private String secret;
}

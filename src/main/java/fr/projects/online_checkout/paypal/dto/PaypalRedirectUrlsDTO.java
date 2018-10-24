package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Data
public class PaypalRedirectUrlsDTO extends PaypalModelDTO {
  private String returnUrl;
  private String cancelUrl;
}

package fr.projects.online_checkout.paypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Getter
@Setter
@Builder
public class PaypalPhoneDTO extends PaypalModelDTO {
  private String countryCode;
  private String nationalNumber;
  private String extension;
}

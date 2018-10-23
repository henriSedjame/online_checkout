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
public class PaypalPayeeDTO extends PaypalModelDTO {
  private String email;
  private String merchantId;
  private String firstName;
  private String lastName;
  private String accountNumber;
  private PaypalPhoneDTO phone;
}

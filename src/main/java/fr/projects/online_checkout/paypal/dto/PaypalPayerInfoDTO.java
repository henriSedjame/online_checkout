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
public class PaypalPayerInfoDTO extends PaypalModelDTO {
  private String email;
  private String buyerAccountNumber;
  private String firstName;
  private String middleName;
  private String lastName;
  private String payerId;
  private String phone;
  private String birthDate;
  private String taxId;
  private String taxIdType;
  private String countryCode;
  private PaypalAddressDTO billingAddress;
}

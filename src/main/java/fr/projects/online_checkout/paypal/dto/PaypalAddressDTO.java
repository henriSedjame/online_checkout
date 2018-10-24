package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */

@Data
public class PaypalAddressDTO extends PaypalModelDTO {
  private String line1;
  private String line2;
  private String city;
  private String countryCode;
  private String postalCode;
  private String state;
  private String phone;
  private String type;
}

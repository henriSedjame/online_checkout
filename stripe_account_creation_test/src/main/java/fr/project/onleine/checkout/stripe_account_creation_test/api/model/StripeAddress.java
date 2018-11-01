package fr.project.onleine.checkout.stripe_account_creation_test.api.model;

import lombok.*;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StripeAddress extends StripeModel {
  private String city;
  private String country;
  private String line1;
  private String line2;
  private String postalCode;
  private String state;
}

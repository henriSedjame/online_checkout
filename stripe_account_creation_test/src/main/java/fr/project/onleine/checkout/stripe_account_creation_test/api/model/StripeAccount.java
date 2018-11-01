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
public class StripeAccount extends StripeModel {
  private String id;
  private StripeLegalEntity legalEntity;


}

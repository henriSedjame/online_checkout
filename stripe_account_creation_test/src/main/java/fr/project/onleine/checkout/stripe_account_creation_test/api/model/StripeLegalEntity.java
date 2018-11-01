package fr.project.onleine.checkout.stripe_account_creation_test.api.model;

import com.stripe.model.Address;
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
public class StripeLegalEntity extends StripeModel {
  private StripeAddress address;
  private String businessName;
  private String firstName;
  private String gender;
  private String lastName;
  private Address personalAddress;
  private String phoneNumber;
  private String taxIdRegistrar;
  private String type;
}

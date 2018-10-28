package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.constraints.Email;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 27/10/2018
 * @Class purposes : .......
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IdentifiantsPaypal {
  private String id;
  @Email
  private String email;
}

package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Coordonnees {
  @Valid
  private Adresse adresse;
  @Valid
  private Phone phone;
  @Email
  private String email;
}

package fr.projects.online_checkout.core.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Coordonnees {
  private Adresse adresse;
  private Phone phone;
  private String email;
}

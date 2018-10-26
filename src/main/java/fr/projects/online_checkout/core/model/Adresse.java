package fr.projects.online_checkout.core.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Adresse {
  private String numero;
  private String rue;
  private String codePostal;
  private String ville;
  private String pays;
  private String infoComplementaires;
}

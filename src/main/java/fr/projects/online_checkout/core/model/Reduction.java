package fr.projects.online_checkout.core.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reduction {
  private double pourcentage;
  private double montant;
}

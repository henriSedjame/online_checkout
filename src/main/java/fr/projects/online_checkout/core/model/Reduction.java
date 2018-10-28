package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reduction {
  @Positive
  @Max(100)
  private double pourcentage;
  @Positive
  private double montant;
}

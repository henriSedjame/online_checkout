package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Panier {
  private Set<@Valid LigneCommande> ligneCommandes = new TreeSet<>();
  @Positive
  private BigDecimal totalHT;
  @Positive
  private BigDecimal totalTTC;
}

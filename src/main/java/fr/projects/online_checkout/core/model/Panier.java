package fr.projects.online_checkout.core.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Panier {
  private Set<LigneCommande> ligneCommandes = new TreeSet<>();
  private BigDecimal totalHT;
  private BigDecimal totalTTC;
}

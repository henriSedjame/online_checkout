package fr.projects.online_checkout.core.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetailPaiement {
  private String description;
  private BigDecimal montantLivraison;
  private BigDecimal montantTaxe;
  private BigDecimal montantReduction;
  private BigDecimal montantFraisGestion;
  private BigDecimal montantHT;
  private BigDecimal montantTTC;
  private double poucentageReduction;
  private double pourcentageTaxe;
  private Devise devisePaiement;
}

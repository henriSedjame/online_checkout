package fr.projects.online_checkout.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailPaiement {
  private BigDecimal montantLivraison;
  private BigDecimal montantTaxe;
  private BigDecimal montantReduction;
  private BigDecimal montantHT;
  private BigDecimal montantTTC;
  private double poucentageReduction;
  private double pourcentageTaxe;
  private Devise devisePaiement;
}

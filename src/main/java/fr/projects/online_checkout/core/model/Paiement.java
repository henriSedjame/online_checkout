package fr.projects.online_checkout.core.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Paiement {
  private String description;
  private Payeur payeur;
  private Beneficiaire beneficiaire;
  private PaiementMethods methodeDePaiement;
  private Panier panier;
  private MotifPaiement motif;
  private Devise devisePaiement;
  private BigDecimal montantLivraison;
  private BigDecimal montantTaxe;
  private BigDecimal montantFraisGestion;
  private double pourcentageTaxe;
}

package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Paiement {
  private String description;
  @Valid
  @NotNull
  private Payeur payeur;
  @Valid
  @NotNull
  private Beneficiaire beneficiaire;
  @NotNull
  private PaiementMethods methodeDePaiement;
  @Valid
  @NotNull
  private Panier panier;
  private MotifPaiement motif;
  @NotNull
  private Devise devisePaiement = Devise.EUR;
  @Positive
  private BigDecimal montantLivraison;
  @Positive
  private BigDecimal montantTaxe;
  @Positive
  private BigDecimal montantFraisGestion;
  @Positive
  @Max(100)
  private double pourcentageTaxe;
}

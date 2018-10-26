package fr.projects.online_checkout.core.model;

import fr.projects.online_checkout.core.utils.PaiementCalculator;
import lombok.Data;

@Data
public class Paiement {
  private Payeur payeur;
  private Beneficiaire beneficiaire;
  private PaiementMethods methodeDePaiement;
  private DetailPaiement detailPaiement;
  private Panier panier;
  private MotifPaiement motif;

  public DetailPaiement getDetailPaiement() {
    return PaiementCalculator.construireDetailPaiement(this);
  }
}

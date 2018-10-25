package fr.projects.online_checkout.model;

import lombok.Data;

@Data
public class Paiement {
  private Payeur payeur;
  private Beneficiaire beneficiaire;
  private PaiementMethods methodeDePaiement;
  private DetailPaiement detailPaiement;
  private Panier panier;
  private MotifPaiement motif;
}

package fr.projects.online_checkout.model;

import lombok.Data;

@Data
public class Panier {
  private java.util.Set<LigneCommande> ligneCommandes;
}

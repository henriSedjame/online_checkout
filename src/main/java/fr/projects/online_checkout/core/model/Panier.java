package fr.projects.online_checkout.core.model;

import lombok.Data;

import java.util.Set;

@Data
public class Panier {
  private Set<LigneCommande> ligneCommandes;
}

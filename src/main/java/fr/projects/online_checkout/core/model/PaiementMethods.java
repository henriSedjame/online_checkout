package fr.projects.online_checkout.core.model;

import lombok.Getter;

@Getter
public enum PaiementMethods {
  PAYPAL("paypal");

  private String libelle;

  PaiementMethods(String libelle) {
    this.libelle = libelle;
  }
}

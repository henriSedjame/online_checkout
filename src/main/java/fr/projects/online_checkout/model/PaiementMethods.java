package fr.projects.online_checkout.model;

import lombok.Getter;

@Getter
public enum PaiementMethods {
  PAYPAL("paypal");

  private String libelle;

  PaiementMethods(String libelle) {
    this.libelle = libelle;
  }
}

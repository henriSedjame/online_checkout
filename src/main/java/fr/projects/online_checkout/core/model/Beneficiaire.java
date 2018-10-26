package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class Beneficiaire extends Personne {
  private String paypalMerchantID;
}

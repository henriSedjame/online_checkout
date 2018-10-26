package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class Payeur extends Personne {
  private String paypalPayerID;
}

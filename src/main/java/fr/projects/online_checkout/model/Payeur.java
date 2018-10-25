package fr.projects.online_checkout.model;

import lombok.Data;

@Data
public class Payeur extends Personne {
  private String paypalPayerID;
}

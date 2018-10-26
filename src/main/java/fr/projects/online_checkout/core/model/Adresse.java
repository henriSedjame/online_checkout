package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class Adresse {
  private String numero;
  private String rue;
  private String codePostal;
  private String ville;
  private String pays;
  private String infoComplementaires;
}

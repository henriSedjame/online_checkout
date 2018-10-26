package fr.projects.online_checkout.core.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Article {
  private String nom;
  private String description;
  private String categorie;
  private BigDecimal prixHT;
  private BigDecimal prixTTC;
  private Reduction reduction;
  private Devise devise;
}

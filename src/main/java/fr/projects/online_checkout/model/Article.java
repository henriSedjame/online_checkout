package fr.projects.online_checkout.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Article {
  private Reduction reduction;
  private String description;
  private BigDecimal prixHT;
  private BigDecimal prixTTC;
}

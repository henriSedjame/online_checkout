package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class CarteBancaire {
  private String cvv;
  private String numero;
  private java.time.LocalDate dateExpiration;
}

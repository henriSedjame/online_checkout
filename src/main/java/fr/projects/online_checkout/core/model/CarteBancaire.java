package fr.projects.online_checkout.core.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CarteBancaire {
  private String cvv;
  private String numero;
  private LocalDate dateExpiration;
}

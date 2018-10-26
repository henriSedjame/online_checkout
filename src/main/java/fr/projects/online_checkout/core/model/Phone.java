package fr.projects.online_checkout.core.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Phone {
  private String indicatif;
  private String numero;
}

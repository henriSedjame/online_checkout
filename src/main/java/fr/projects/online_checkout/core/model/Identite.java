package fr.projects.online_checkout.core.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Identite {
  private String nom;
  private String prenom;
  private LocalDate dateNaissance;
}

package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.constraints.Past;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Identite {
  private String nom;
  private String prenom;
  @Past
  private LocalDate dateNaissance;
}

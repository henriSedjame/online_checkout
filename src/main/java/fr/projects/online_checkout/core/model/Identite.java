package fr.projects.online_checkout.core.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Identite {
  private String nom;
  private String prenom;
  private LocalDate dateNaissance;
}

package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class Coordonnees {
  private Adresse adresse;
  private Phone phone;
  private String email;
}

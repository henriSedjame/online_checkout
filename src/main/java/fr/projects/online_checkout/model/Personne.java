package fr.projects.online_checkout.model;

import lombok.Data;

import java.util.Set;
import java.util.TreeSet;

@Data
public class Personne {
  private TypePersonne type;
  private Coordonnees coordonnees;
  private Identite identite;
  private Set<CarteBancaire> carteBancaires = new TreeSet<>();
}

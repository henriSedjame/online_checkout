package fr.projects.online_checkout.core.model;

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

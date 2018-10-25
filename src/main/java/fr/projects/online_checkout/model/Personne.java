package fr.projects.online_checkout.model;

import java.util.Set;
import java.util.TreeSet;

public class Personne {
  private TypePersonne type;
  private Coordonnees coordonnees;
  private Identite identite;
  private Set<CarteBancaire> carteBancaires = new TreeSet<>();
}

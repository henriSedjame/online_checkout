package fr.projects.online_checkout.core.model;

import lombok.*;

import java.util.Set;
import java.util.TreeSet;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Personne {
  private TypePersonne type;
  private Coordonnees coordonnees;
  private Identite identite;
  private Set<CarteBancaire> carteBancaires = new TreeSet<>();
  private IdentifiantsPaypal identifiantsPaypal;
}

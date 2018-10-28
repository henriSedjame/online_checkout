package fr.projects.online_checkout.core.model;

import lombok.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.TreeSet;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Personne {
  private TypePersonne type = TypePersonne.PHYSIQUE;
  @Valid
  private Coordonnees coordonnees;
  @Valid
  private Identite identite;
  private Set<@Valid CarteBancaire> carteBancaires = new TreeSet<>();
  @Valid
  private IdentifiantsPaypal identifiantsPaypal;
}

package fr.projects.online_checkout.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Beneficiaire extends Personne {
  private String paypalMerchantID;
}

package fr.projects.online_checkout.paypal.constants;

import lombok.Getter;

/**
 * @Project project_mythe
 * @Author Henri Joel SEDJAME
 * @Date 20/10/2018
 * @Class purposes : .......
 */
@Getter
public enum PaypalIntents {

  AUTHORIZE("authorize"), SALE("sale"), ORDER("order");

  private String libelle;

  PaypalIntents(String libelle) {
    this.libelle = libelle;
  }
}

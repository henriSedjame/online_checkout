package fr.projects.online_checkout.paypal.configuration;

import lombok.Getter;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */
@Getter
public enum PaypalPaymentMethods {
  PAYPAL("paypal");

  private String libelle;

  PaypalPaymentMethods(String libelle) {
    this.libelle = libelle;
  }
}

package fr.projects.online_checkout.model;

import lombok.Getter;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 25/10/2018
 * @Class purposes : .......
 */
@Getter
public enum MotifPaiement {
  VENTE("sale"), COMMANDE("order"), AUTHORISATION("authorize");

  private String paypalLibelle;

  MotifPaiement(String paypalLibelle) {
    this.paypalLibelle = paypalLibelle;
  }
}

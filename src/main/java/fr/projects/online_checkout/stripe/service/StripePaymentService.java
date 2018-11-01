package fr.projects.online_checkout.stripe.service;

import com.stripe.model.Charge;
import reactor.core.publisher.Mono;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
public interface StripePaymentService {

  /**
   * Méthode permettant de charger un compte stripe à partir d'un token
   *
   * @param totalAmount
   * @param destinationAmount
   * @param currency
   * @param destinationAccountId
   * @param token
   * @return
   */
  Mono<Charge> createCharge(Long totalAmount, Long destinationAmount, String currency, String destinationAccountId, String token);
}

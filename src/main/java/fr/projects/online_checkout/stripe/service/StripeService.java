package fr.projects.online_checkout.stripe.service;

import com.stripe.model.Account;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import reactor.core.publisher.Mono;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
public interface StripeService {

  /**
   * Methode permettant de créer un compte stripe
   *
   * @return
   */
  Mono<Account> createStripeAccount() throws StripePaymentException;
}

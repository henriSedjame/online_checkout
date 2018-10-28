package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import fr.projects.online_checkout.stripe.configuration.StripeConfig;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionBuilder;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionMessages;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import fr.projects.online_checkout.stripe.service.StripeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
@Service
public class StripeServiceImpl implements StripeService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private StripeConfig config;
  private StripeExceptionBuilder exceptionBuilder;
  private StripeExceptionMessages exceptionMessages;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************

  public StripeServiceImpl(StripeConfig config, StripeExceptionMessages exceptionMessages) {
    this.config = config;
    this.exceptionMessages = exceptionMessages;
    exceptionBuilder = new StripeExceptionBuilder(StripePaymentException.class);
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************
  @Override
  public Mono<Account> createStripeAccount() throws StripePaymentException {
    this.exceptionBuilder.clear();

    Map<String, Object> params = new HashMap<>();
    params.put(config.getAccountCountryName(), config.getAccountCountry());
    params.put(config.getAccountTypeName(), config.getAccountType());

    final Mono<Account> createdAccount = Mono.fromSupplier(() -> {
      try {
        return Account.create(params);
      } catch (StripeException e) {
        this.exceptionBuilder.addException(exceptionMessages.STRIPE_CREATION_COMPTE_IMPOSSIBLE, e);
      }
      return null;
    });

    this.exceptionBuilder.throwException();

    return Objects.nonNull(createdAccount) ? createdAccount : Mono.empty();
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************

}

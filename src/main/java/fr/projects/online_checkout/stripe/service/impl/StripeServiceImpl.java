package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.net.RequestOptions;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.stripe.configuration.StripeConfig;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionBuilder;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionMessages;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import fr.projects.online_checkout.stripe.service.StripeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
  public Mono<Account> createStripeAccount() {
    this.exceptionBuilder.clear();

    Map<String, Object> params = new HashMap<>();
    params.put(config.getAccountCountryName(), config.getAccountCountry());
    params.put(config.getAccountTypeName(), config.getAccountType());

    return Mono.defer(() -> {
      try {
        return Mono.just(Account.create(params));
      } catch (StripeException e) {
        return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.STRIPE_CREATION_COMPTE_IMPOSSIBLE, e));
      }

    });

  }

  @Override
  public Mono<Account> retrieveAccount(String accountId) {
    this.exceptionBuilder.clear();

    Stripe.clientId = accountId;

    RequireObjects.requireNotNull(Arrays.asList(accountId), this.exceptionBuilder, exceptionMessages.RETRIEVE_ACCOUNT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {
      RequestOptions options = RequestOptions.getDefault();
      return Mono.defer(() -> {
        try {
          return Mono.just(Account.retrieve(accountId, options));
        } catch (StripeException e) {
          return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(exceptionMessages.STRIPE_RECUPERATION_COMPTE_IMPOSSIBLE, accountId), e));
        }
      });
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public Mono<Account> updateAccount(String accountId, Map<String, Object> paramToUpdate) {
    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(accountId), this.exceptionBuilder, exceptionMessages.RETRIEVE_ACCOUNT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {

      return Mono.defer(() -> this.retrieveAccount(accountId).flatMap(account -> {
        try {
          return Mono.just(account.update(paramToUpdate));
        } catch (StripeException e) {
          return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(exceptionMessages.STRIPE_UPDATE_COMPTE_IMPOSSIBLE, accountId), e));
        }
      }));
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************

}

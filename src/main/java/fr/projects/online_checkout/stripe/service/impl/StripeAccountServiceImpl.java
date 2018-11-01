package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.CountrySpec;
import com.stripe.model.File;
import com.stripe.net.RequestOptions;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionBuilder;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionMessages;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import fr.projects.online_checkout.stripe.service.StripeAccountService;
import fr.projects.online_checkout.stripe.utils.StripeParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

import static fr.projects.online_checkout.stripe.model.StripeAccountParams.*;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
@Service
@Slf4j
public class StripeAccountServiceImpl implements StripeAccountService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private StripeExceptionBuilder exceptionBuilder;
  private StripeExceptionMessages exceptionMessages;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************

  public StripeAccountServiceImpl(StripeExceptionMessages exceptionMessages) {
    this.exceptionMessages = exceptionMessages;
    exceptionBuilder = new StripeExceptionBuilder(StripePaymentException.class);
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  @Override
  public Mono<Account> createStripeAccount(String countryCode, String accountType, String token) {
    this.exceptionBuilder.clear();

    final Map<String, Object> params = StripeParams.create()
      .put("country", countryCode)
      .put("type", accountType)
      .put("account_token", token)
      .build();

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

    RequireObjects.requireNotNull(Arrays.asList(accountId), this.exceptionBuilder, exceptionMessages.RETRIEVE_ACCOUNT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {
      RequestOptions options = RequestOptions.builder()
        .setStripeAccount(accountId)
        .build();
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

  @Override
  public Mono<File> updateAccountUsingFile(String accountId, java.io.File dataFile) {
    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(dataFile), this.exceptionBuilder, MessageFormat.format(this.exceptionMessages.UPDATE_DATA_FILE_MISSING, accountId));

    if (this.exceptionBuilder.isEmpty()) {

      final Map<String, Object> fileParams = StripeParams.create()
        .put("purpose", "")
        .put("file", dataFile)
        .build();

      return Mono.defer(() -> {
        try {
          return Mono.just(File.create(fileParams));
        } catch (Exception e) {
          return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(exceptionMessages.STRIPE_UPDATE_COMPTE_IMPOSSIBLE, accountId), e));
        }
      });
    }
    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public Mono<Account> deleteAccount(String accountId) {
    this.exceptionBuilder.clear();

    return Mono.defer(() -> this.retrieveAccount(accountId)
      .flatMap(account -> {
        try {
          return Mono.just(account.delete());
        } catch (StripeException e) {
          return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.STRIPE_SUPPRESSION_COMPTE_IMPOSSIBLE, accountId), e));
        }
      }));
  }

  @Override
  public Mono<Account> acceptServicesAgreement(String accountId) {
    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(accountId), this.exceptionBuilder, exceptionMessages.RETRIEVE_ACCOUNT_PARAMETERS_MISSING);

    if (this.exceptionBuilder.isEmpty()) {

      String clientIpAddress;
      try {
        clientIpAddress = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
        return Mono.error(this.exceptionBuilder.buildException(exceptionMessages.CLIENT_IP_RETRIEVE_IMPOSSIBLE, e));
      }

      final Map<String, Object> tosAcceptanceParams = StripeParams.create()
        .put(ACCEPTANCE_DATE, (long) System.currentTimeMillis() / 1000L)
        .put(ACCEPTANCE_IP, clientIpAddress)
        .build();

      final Map<String, Object> params = StripeParams.create()
        .put(TOS_ACCEPTANCE, tosAcceptanceParams)
        .build();

      return this.updateAccount(accountId, params);
    }

    return Mono.error(this.exceptionBuilder.buildException());
  }

  @Override
  public CountrySpec getStripeSpecificication(String countryCode) throws StripeException {
    return CountrySpec.retrieve(countryCode);
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************

}

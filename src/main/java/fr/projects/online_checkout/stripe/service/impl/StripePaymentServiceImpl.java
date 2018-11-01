package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import fr.projects.online_checkout.core.utils.RequireObjects;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionBuilder;
import fr.projects.online_checkout.stripe.exceptions.StripeExceptionMessages;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import fr.projects.online_checkout.stripe.service.StripePaymentService;
import fr.projects.online_checkout.stripe.utils.StripeParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
@Service
@Slf4j
public class StripePaymentServiceImpl implements StripePaymentService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private StripeExceptionBuilder exceptionBuilder;
  private StripeExceptionMessages exceptionMessages;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************


  public StripePaymentServiceImpl(StripeExceptionMessages exceptionMessages) {
    this.exceptionBuilder = new StripeExceptionBuilder(StripePaymentException.class);
    this.exceptionMessages = exceptionMessages;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  @Override
  public Mono<Charge> createCharge(Long totalAmount, Long destinationAmount, String currency, String destinationAccountId, String token) {
    this.exceptionBuilder.clear();

    RequireObjects.requireNotNull(Arrays.asList(totalAmount, currency, destinationAccountId, token), this.exceptionBuilder, "");

    if (this.exceptionBuilder.isEmpty()) {

      final Map<String, Object> destinationParams = StripeParams.create()
        .put("account", destinationAccountId)
        .ifTruePut(Objects.nonNull(destinationAmount), "amount", destinationAmount)
        .build();

      final Map<String, Object> params = StripeParams.create()
        .put("amount", totalAmount)
        .put("cuurency", currency)
        .put("source", token)
        .put("destination", destinationParams)
        .build();

      return Mono.defer(() -> {
        try {
          return Mono.just(Charge.create(params));
        } catch (StripeException e) {
          return Mono.error(this.exceptionBuilder.buildException(this.exceptionMessages.STRIPE_CHARGE_CREATION_IMPOSSIBLE, e));
        }
      });

    }

    return Mono.error(this.exceptionBuilder.buildException());
  }


  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************
}

package fr.projects.online_checkout.paypal.services.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.paypal.configuration.PayPalConfig;
import fr.projects.online_checkout.paypal.configuration.PaypalConstants;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionBuilder;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionMessages;
import fr.projects.online_checkout.paypal.exceptions.PaypalPaymentException;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


@Service
@Getter
@Slf4j
public class PaypalPaymentServiceImpl implements PaypalPaymentService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************
  private PayPalConfig config;

  private PaypalExceptionBuilder<PaypalPaymentException> exceptionBuilder;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public PaypalPaymentServiceImpl(PayPalConfig config) {
    this.exceptionBuilder = new PaypalExceptionBuilder<>(PaypalPaymentException.class);
    this.config = config;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************
  @Override
  public Mono<Payment> approvePayment(Payment payment) throws PayPalRESTException {
    this.exceptionBuilder.clear();

    final Mono<Payment> createdPayment = Mono.fromSupplier(() -> {
      try {
        return payment.create(config.getContext());
      } catch (Exception e) {
        exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.APPROBATION_PAYMENT_IMPOSSIBLE));
      }
      return null;
    });

    this.exceptionBuilder.throwException();

    return createdPayment;
  }

  @Override
  public Mono<Payment> executePayment(String paymentId, String payerId) throws PayPalRESTException {
    this.exceptionBuilder.clear();

    if (Objects.isNull(paymentId)) this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ID_NULL));
    if (Objects.isNull(payerId)) this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYER_ID_NULL));

    if (!exceptionBuilder.throwException()){

      Payment payment = new Payment().setId(paymentId);
      PaymentExecution execution = new PaymentExecution().setPayerId(payerId);

      final Mono<Payment> executedPayment = Mono.fromSupplier(() -> {
        try {
          return payment.execute(this.config.getContext(), execution);
        } catch (Exception e) {
          this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.EXECUTION_PAYMENT_IMPOSSIBLE));
        }
        return null;
      });

      this.exceptionBuilder.throwException();

      return executedPayment;
    }
    return Mono.empty();
  }

  @Override
  public Mono<Authorization> authorizePayment(String paymentId, String payerId) throws PayPalRESTException {

    this.exceptionBuilder.clear();

    if (Objects.isNull(paymentId)) this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ID_NULL));
    if (Objects.isNull(payerId)) this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYER_ID_NULL));

    if (!this.exceptionBuilder.throwException()) {

      final Mono<Authorization> authorization = this.executePayment(paymentId, payerId)
        .map(payment ->{
          RelatedResources relatedResource = getPaymentRelatedResources(payment);
          if (!this.exceptionBuilder.hasException()) return relatedResource.getAuthorization();
          return null;
        });

      this.exceptionBuilder.throwException();

      return authorization;
    }

    return Mono.empty();
  }

  @Override
  public Mono<Capture> capturePayment(Authorization authorization) throws PayPalRESTException {

    this.exceptionBuilder.clear();

    final Amount amount = authorization.getAmount();
    if (Objects.isNull(amount)) this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_AMOUNT_NULL));

    if (! this.exceptionBuilder.throwException()){
      Capture capture = new Capture()
        .setAmount(amount)
        .setIsFinalCapture(true);

      final Mono<Capture> responseCapture = Mono.fromSupplier( () -> {
        try {
          return authorization.capture(this.config.getContext(), capture);
        } catch (PayPalRESTException e) {
          this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_AUTHORIZATION_CAPTURE_IMPOSSIBLE));
        }
        return null;
      });

      this.exceptionBuilder.throwException();

      return responseCapture;
    }
    return Mono.empty();
  }

  @Override
  public Mono<Capture> captureOrderPayment(String paymentId, String payerId) throws PayPalRESTException {

    Mono<Capture> orderCaptured = this.executePayment(paymentId, payerId)
            .map(payment -> {
              if (Objects.equals(payment.getState(), PaypalConstants.APPROVED)) {

                String orderId = getPaymentRelatedResources(payment).getOrder().getId();

                if(Objects.isNull(orderId))
                  this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ORDER_ID_NULL));

                Capture responseCapture = null;

                if (!this.exceptionBuilder.hasException()) {
                  Amount amount = payment.getTransactions().get(0).getAmount();

                  if (Objects.isNull(amount))
                    this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_AMOUNT_NULL));

                  Order order = null;
                  try {
                    order = Order.get(this.config.getContext(), orderId)
                            .setAmount(amount);
                  } catch (PayPalRESTException e) {
                    this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ORDER_CREATION_IMPOSSIBLE));
                  }

                  if (!this.exceptionBuilder.hasException()) {
                    Authorization authorization = null;
                    try {
                      authorization = order.authorize(this.config.getContext());
                    } catch (PayPalRESTException e) {
                      this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ORDER_AUTHORIZATION_IMPOSSIBLE));
                    }

                    if (!this.exceptionBuilder.hasException()) {
                      Capture capture = new Capture()
                              .setAmount(amount)
                              .setIsFinalCapture(true);
                      try {
                        responseCapture = authorization.capture(this.config.getContext(), capture);
                      } catch (PayPalRESTException e) {
                        this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_ORDER_CAPTURE_IMPOSSIBLE));
                      }
                    }
                  }
                }
                return responseCapture;
              } else {
                this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_NOT_APPROVED));
                return null;
              }
            });

    if(!this.exceptionBuilder.throwException()) return orderCaptured;

    return Mono.empty();
  }

  //********************************************************************************************************************
  // PRIVATE METHODES
  //********************************************************************************************************************

  private RelatedResources getPaymentRelatedResources(Payment payment) {
    final List<Transaction> transactions = payment.getTransactions();
    Transaction transaction = null;
    List<RelatedResources> relatedResources = null;
    RelatedResources relatedResource = null;
    if (transactions.isEmpty())
      this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_TRANSACTIONS_EMPTY));
    else {
      transaction = transactions.get(0);
      if (Objects.isNull(transaction))
        this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_TRANSACTION_NULL));
      else {
        relatedResources = transaction.getRelatedResources();
        if (relatedResources.isEmpty())
          this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_TRANSACTION_RELATED_SOURCES_EMPTY));
        else {
          relatedResource = relatedResources.get(0);
          if (Objects.isNull(relatedResource))
            this.exceptionBuilder.addException(new PaypalPaymentException(PaypalExceptionMessages.PAYMENT_TRANSACTION_RELATED_SOURCE_NULL));
        }
      }
    }
    return relatedResource;
  }

}

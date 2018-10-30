package fr.projects.online_checkout.paypal.services;

import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Refund;
import com.paypal.base.rest.PayPalRESTException;
import reactor.core.publisher.Mono;


public interface PaypalPaymentService {

  /**
   * Méthode permettant de faire approuver un paiement paypal par le client
   * @param payment
   * @return
   */
  Mono<Payment> approvePayment(Payment payment);

  /**
   * Méthode permettant d'éxécuter un paiement approuvé
   * @param paymentId
   * @param payerId
   * @return
   */
  Mono<Payment> executePayment(String paymentId, String payerId);

  /**
   * Méthode permettant de d'executer le paiement d'une commande paypal
   * @param paymentId
   * @param payerId
   * @return
   */
  Mono<Capture> captureOrderPayment(String paymentId, String payerId, double amountToRefund, String currency);

  /**
   * Methode permettant le remboursement d'un paiement paypal
   *
   * @param saleId
   * @param amountToRefund
   * @param currency
   * @return
   * @throws PayPalRESTException
   */
  Mono<Refund> refundPayment(String saleId, double amountToRefund, String currency);

  /**
   * Méthode permettant d'éxécuter une authorisation de paiement paypal
   * @param paymentId
   * @param payerId
   * @return
   * @throws PayPalRESTException
   */
  Mono<Capture> captureAuthorizationPayment(String paymentId, String payerId, double amountToRefund, String currency);

}

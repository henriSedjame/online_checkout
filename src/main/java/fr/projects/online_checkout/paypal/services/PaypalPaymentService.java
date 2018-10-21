package fr.projects.online_checkout.paypal.services;

import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PaypalPaymentService {

  /**
   * Méthode permettant de faire approuver un paiement paypal par le client
   * @param payment
   * @return
   * @throws PayPalRESTException
   */
  Mono<Payment> approvePayment(Payment payment) throws PayPalRESTException;


  /**
   * Méthode permettant d'éxécuter un paiement approuvé
   * @param paymentId
   * @param payerId
   * @return
   * @throws PayPalRESTException
   */
  Mono<Payment> executePayment(String paymentId, String payerId) throws PayPalRESTException;

  /**
   * Méthode permettant d'authoriser un paiement approuvé en différé par un clien paypal
   * @param paymentId
   * @param payerId
   * @return
   * @throws PayPalRESTException
   */
  Mono<Authorization> authorizePayment(String paymentId, String payerId) throws PayPalRESTException;

  /**
   * Méthode permettant d'éxécuter une authorisation de paiment paypal
   * @param authorization
   * @return
   * @throws PayPalRESTException
   */
  Mono<Capture> capturePayment(Authorization authorization) throws PayPalRESTException;


}

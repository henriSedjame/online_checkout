package fr.projects.online_checkout.paypal.controller;

import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.paypal.configuration.PaypalConstants;
import fr.projects.online_checkout.paypal.mapper.PayPalPaymentMapper;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Component
public class RouterHandlers {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************
  private PaypalPaymentService paymentService;
  private PayPalPaymentMapper paymentMapper;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public RouterHandlers(PaypalPaymentService paymentService, PayPalPaymentMapper paymentMapper) {
    this.paymentService = paymentService;
    this.paymentMapper = paymentMapper;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************
  public Mono<ServerResponse> executePaymentHandled(ServerRequest request) {
    return this.executePayment(request)
      .onErrorResume(e -> Mono.just(e.getMessage()))
      .flatMap(response -> ServerResponse.ok().syncBody(response));
  }

  //********************************************************************************************************************
  // METHODES PRIVATE
  //********************************************************************************************************************


  private Mono<String> executePayment(ServerRequest request) {
    final String paymentId = request.queryParam(PaypalConstants.PAYMENT_ID).orElse(null);
    final String payertId = request.queryParam(PaypalConstants.PAYER_ID).orElse(null);
    try {
      return paymentService.executePayment(paymentId, payertId).map(payment -> paymentMapper.toDTO(payment).toString());
    } catch (PayPalRESTException e) {
      return Mono.error(e);
    }
  }
}

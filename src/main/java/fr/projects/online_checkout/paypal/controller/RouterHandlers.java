package fr.projects.online_checkout.paypal.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.paypal.configuration.PaypalConstants;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public RouterHandlers(PaypalPaymentService paymentService) {
    this.paymentService = paymentService;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  public Mono<ServerResponse> executePayment(ServerRequest serverRequest) throws PayPalRESTException {
    MultiValueMap<String, String> queryParams = serverRequest.queryParams();
    String paymentId = queryParams.getFirst(PaypalConstants.PAYMENT_ID);
    String payerId = queryParams.getFirst(PaypalConstants.PAYER_ID);
    return ServerResponse
            .ok()
            .body(paymentService.executePayment(paymentId, payerId), Payment.class);
  }
}

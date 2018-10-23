package fr.projects.online_checkout.paypal.controller


import fr.projects.online_checkout.paypal.configuration.PaypalConstants
import fr.projects.online_checkout.paypal.services.PaypalPaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RouterHandlersKt {

    @Autowired
    lateinit var paymentService: PaypalPaymentService;

    fun executePayment(request: ServerRequest) : Mono<ServerResponse> {
        val paymentId = request.queryParam(PaypalConstants.PAYMENT_ID).orElse(null);
        val payerId = request.queryParam(PaypalConstants.PAYER_ID).orElse(null);
        return paymentService.executePayment(paymentId, payerId)
                .flatMap { ServerResponse.ok().syncBody(it) }
                .onErrorResume { ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(it.message) }
    }

}
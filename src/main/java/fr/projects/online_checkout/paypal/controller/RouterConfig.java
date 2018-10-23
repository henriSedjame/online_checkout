package fr.projects.online_checkout.paypal.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> routerFunction(RouterHandlers handlers) {
    return RouterFunctions
      .route(RequestPredicates.GET(Routes.EXECUTE), handlers::executePaymentHandled);
  }
}

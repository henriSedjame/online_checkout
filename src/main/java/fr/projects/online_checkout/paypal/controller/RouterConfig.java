package fr.projects.online_checkout.paypal.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static fr.projects.online_checkout.paypal.controller.Routes.*;

@Configuration
public class RouterConfig {

  @Bean
  RouterFunction<?> routerFunction(RouterHandlersKt handlers) {
    return RouterFunctions
            .route(RequestPredicates.GET( "/"+EXECUTE),handlers::executePayment);

  }
}

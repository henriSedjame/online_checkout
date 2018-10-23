package fr.projects.online_checkout.paypal.controller

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.*

@Configuration
open class RouterConfigKt {

    @Bean
    open fun  routerFunction(handlers:RouterHandlersKt ) : RouterFunction<ServerResponse>  = router {
        GET(Routes.EXECUTE, handlers::executePayment)

    }

}
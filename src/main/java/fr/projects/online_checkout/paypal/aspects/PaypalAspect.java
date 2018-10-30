package fr.projects.online_checkout.paypal.aspects;

import com.paypal.base.rest.APIContext;
import fr.projects.online_checkout.paypal.configuration.PaypalApi;
import fr.projects.online_checkout.paypal.constants.PaypalMode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PaypalAspect {

  @Value("${paypal.api.rest.clientId}")
  private String clientId;
  @Value("${paypal.api.rest.secret}")
  private String secret;

  @Before("paypalPointCut()")
  public void paypalContextAdvice() {
    log.info(" **** Dans l'aspect *********");
    PaypalApi.context = new APIContext(clientId, secret, PaypalMode.SANDBOX.name().toLowerCase());
  }

  @Pointcut("execution(public * fr.projects.online_checkout.paypal.services.PaypalPaymentService.*(..))")
  public void paypalPointCut() {
  }
}

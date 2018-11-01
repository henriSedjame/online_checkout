package fr.projects.online_checkout.stripe.aspects;

import com.stripe.Stripe;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class StripeAspect {

  @Before("stripePointCut()")
  public void stripeSecretKeyAdvice() {
    Stripe.apiKey = "sk_test_loMFrgA9NHBdvGjarRw76ntt";
  }

  @Pointcut("execution(public * fr.projects.online_checkout.stripe.service.impl.*.*(..))")
  public void stripePointCut() {
  }

}

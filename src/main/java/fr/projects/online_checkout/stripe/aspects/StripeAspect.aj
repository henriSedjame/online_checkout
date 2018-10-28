package fr.projects.online_checkout.stripe.aspects;

import com.stripe.Stripe;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 *@Project online_checkout
 *@Author Henri Joel SEDJAME
 *@Date 28/10/2018
 *
 *@Class purposes : .......
 */
@Aspect
@Component
@Slf4j
public aspect StripeAspect {

  @Before("stripePointCut()")
  public void stripeSecretKeyAdvice() {
    Stripe.apiKey = "sk_test_loMFrgA9NHBdvGjarRw76ntt";
  }

  @Pointcut("execution(public * fr.projects.online_checkout.stripe.service.impl.StripeServiceImpl.*(..))")
  public void stripePointCut() {
  }
}

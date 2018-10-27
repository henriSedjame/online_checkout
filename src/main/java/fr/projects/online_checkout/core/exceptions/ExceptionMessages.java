package fr.projects.online_checkout.core.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 27/10/2018
 * @Class purposes : .......
 */
@Configuration
@PropertySource("classpath:error_messages.properties")
public class ExceptionMessages {

  @Value("${paypal_merchand_email_null}")
  public String PAYPAL_MERCHAND_EMAIL_NULL;

}

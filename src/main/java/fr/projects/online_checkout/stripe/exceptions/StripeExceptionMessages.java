package fr.projects.online_checkout.stripe.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
@Configuration
@PropertySource("classpath:stripe_error_messages.properties")
public class StripeExceptionMessages {
  @Value("${stripe.creation.compte.impossible}")
  public String STRIPE_CREATION_COMPTE_IMPOSSIBLE;
  @Value("${stripe.recuperation.compte.impossible}")
  public String STRIPE_RECUPERATION_COMPTE_IMPOSSIBLE;
  @Value("${stripe.update.compte.impossible}")
  public String STRIPE_UPDATE_COMPTE_IMPOSSIBLE;
  @Value("${retrieve.account.parameters.missing}")
  public String RETRIEVE_ACCOUNT_PARAMETERS_MISSING;
  @Value("${client.ip.address.retrieve.impossible}")
  public String CLIENT_IP_RETRIEVE_IMPOSSIBLE;
  public String UPDATE_DATA_FILE_MISSING;
}

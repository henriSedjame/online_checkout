package fr.projects.online_checkout.stripe.configuration;

import lombok.Getter;
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
@PropertySource("classpath:stripe.properties")
@Getter
public class StripeConfig {
  @Value("${account.country.value}")
  private String accountCountry;
  @Value("${account.country.name}")
  private String accountCountryName;
  @Value("${account.type.value}")
  private String accountType;
  @Value("${account.type.name}")
  private String accountTypeName;

}

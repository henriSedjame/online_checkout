package fr.projects.online_checkout.paypal.configuration;

import com.paypal.base.rest.APIContext;
import fr.projects.online_checkout.paypal.constants.PaypalMode;
import fr.projects.online_checkout.paypal.model.PaypalClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Configuration
public class PayPalConfig {

  @Value("${paypal.api.rest.clientId}")
  private String clientId;
  @Value("${paypal.api.rest.secret}")
  private String secret;

  private APIContext context;

  public APIContext getContext() {
    if (Objects.isNull(context) || Objects.isNull(context.getClientID())|| Objects.isNull(context.getClientSecret()))
      context = new APIContext(clientId, secret, PaypalMode.SANDBOX.name().toLowerCase());
    return this.context;
  }

  public PaypalClient getClient() {
    return PaypalClient.builder()
      .clientId(clientId)
      .secret(secret)
      .build();
  }
}

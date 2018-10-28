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

  @Value("${payeur.null}")
  public String PAYEUR_NULL;
  @Value("${beneficiaire.null}")
  public String BENEFICIAIRE_NULL;
  @Value("${paypal.merchand.email.null}")
  public String PAYPAL_MERCHAND_EMAIL_NULL;
  @Value("${mode.paiement.incorrect}")
  public String MODE_PAIEMENT_INCORRECT;
  @Value("${panier.null}")
  public String PANIER_NULL;
  @Value("${motif.null}")
  public String MOTIF_NULL;
}

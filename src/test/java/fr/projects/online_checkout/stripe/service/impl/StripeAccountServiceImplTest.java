package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import fr.projects.online_checkout.stripe.service.StripeAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Ignore
public class StripeAccountServiceImplTest {

  @Autowired
  StripeAccountService service;

  @Test
  public void createStripeAccount() {
    final Mono<Account> stripeAccount = service.createStripeAccount("FR", "custom", "");

    stripeAccount.subscribe(account -> {
      log.info(account.toJson());
    }, error -> {
      log.error(error.getMessage(), error.getCause());
    });
  }

  @Test
  public void retrieveAccount() {
  }

  @Test
  public void updateAccount() {
  }

  @Test
  public void updateAccountUsingFile() {
  }

  @Test
  public void acceptServicesAgreement() {
  }

  @Test
  public void getStripeSpecificication() throws StripeException {
    log.info(this.service.getStripeSpecificication("FR").toJson());
  }

  @Test
  public void deleteAccount() throws StripePaymentException {
    this.service.deleteAccount("acct_1DRgHEGhMnRSbhr1").subscribe(
      account -> {
        log.info(account.toJson());
        assertEquals(true, account.getDeleted());
      },
      error -> {
        log.error(error.getMessage());
        fail(error.getMessage(), error);
      },
      () -> log.info("Test de suppresion terminée avec succès")
    );
  }
}

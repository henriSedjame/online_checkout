package fr.projects.online_checkout.stripe.service.impl;

import com.stripe.exception.StripeException;
import fr.projects.online_checkout.stripe.service.StripeAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StripeAccountServiceImplTest {

  @Autowired
  StripeAccountService service;

  @Test
  public void createStripeAccount() {
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
}
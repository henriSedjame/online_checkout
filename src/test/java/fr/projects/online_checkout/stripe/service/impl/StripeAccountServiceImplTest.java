package fr.projects.online_checkout.stripe.service.impl;

import fr.projects.online_checkout.stripe.service.StripeAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 30/10/2018
 * @Class purposes : .......
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StripeAccountServiceImplTest {

  @Autowired
  StripeAccountService service;

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
  }

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

}

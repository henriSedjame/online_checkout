package fr.projects.online_checkout.stripe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StripeConfigTest {
  @Autowired
  StripeConfig config;

  @Test
  public void getAccountCountry() {
    final String accountCountry = config.getAccountCountry();
    assertEquals("FR", accountCountry);
  }

  @Test
  public void getAccountCountryName() {
    final String accountCountryName = config.getAccountCountryName();
    assertEquals("country", accountCountryName);
  }

  @Test
  public void getAccountType() {
    final String accountType = config.getAccountType();
    assertEquals("custom", accountType);
  }

  @Test
  public void getAccountTypeName() {
    final String accountTypeName = config.getAccountTypeName();
    assertEquals("type", accountTypeName);
  }
}

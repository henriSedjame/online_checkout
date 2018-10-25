package fr.projects.online_checkout.paypal.services.impl;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fr.projects.online_checkout.paypal.configuration.PayPalConfig;
import fr.projects.online_checkout.paypal.constants.PaypalMode;
import fr.projects.online_checkout.paypal.exceptions.PaypalExceptionMessages;
import fr.projects.online_checkout.paypal.model.PaypalClient;
import fr.projects.online_checkout.paypal.providers.PaypalPaymentsProviders;
import fr.projects.online_checkout.paypal.services.PaypalPaymentService;
import fr.projects.online_checkout.paypal.utils.PaypalPaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PaypalPaymentServiceImplTest {

  Payment payment;
  Payment authorizationPayment;
  Payment orderPayment;
  PaypalClient client;
  String mode;
  PaypalPaymentService service;

  @Autowired
  PayPalConfig config;
  @Autowired
  PaypalExceptionMessages exceptionMessages;

  @Before
  public void setUp() {
    service = new PaypalPaymentServiceImpl(exceptionMessages);

    payment = PaypalPaymentsProviders.createAPayment();
    authorizationPayment = PaypalPaymentsProviders.createAuthorizationPayment();
    orderPayment = PaypalPaymentsProviders.createOrderPayment();
    client = config.getClient();
    mode = PaypalMode.SANDBOX.name().toLowerCase();
  }

  @Test
  public void shouldBeNotNull(){
    assertAll( "test",
      () -> assertNotNull(config),
      () -> assertNotNull(exceptionMessages),
      () -> assertNotNull(service),
      () -> assertNotNull(payment),
      () -> assertNotNull(authorizationPayment),
      () -> assertNotNull(orderPayment),
      () -> assertNotNull(client));
  }


  @After
  public void tearDown() {
  }

  @Test
  public void approvePayment() {
  }

  @Test
  public void executePayment() throws PayPalRESTException {
    service.approvePayment(payment, client, mode).subscribe(response -> {
      assertNotNull(response);
      String approvalUrl = PaypalPaymentUtils.getApprovalUrl(response);
      assertNotNull(approvalUrl);
      log.info(approvalUrl);
    });
  }

  @Test
  public void authorizePayment() {

  }

  @Test
  public void capturePayment() {
  }

  @Test
  public void captureOrderPayment() {
  }
}

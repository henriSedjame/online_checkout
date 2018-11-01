package fr.project.onleine.checkout.stripe_account_creation_test.controller;

import fr.project.onleine.checkout.stripe_account_creation_test.api.model.StripeAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
@Controller
public class StripeController {

  @RequestMapping("start")
  public String start() {
    return "account";
  }

  @RequestMapping("create-account")
  public String createAccount(HttpServletRequest request) {
    RestTemplate template = new RestTemplate();
    final String token = request.getParameter("token");
    System.out.println("TOKEN : " + token);
    String uri = "http://localhost:8082/account/create/" + token;
    final ResponseEntity<StripeAccount> account = template.getForEntity(uri, StripeAccount.class);
    System.out.println(account.getBody().toJson());
    return "index";
  }

  @RequestMapping("pay")
  public String goToPay() {
    return "stripe_payment";
  }

  @RequestMapping("charge")
  public String chargeCard(HttpServletRequest request) {
    RestTemplate template = new RestTemplate();
    final String token = request.getParameter("token");
    System.out.println("TOKEN : " + token);
    return "index";
  }
}

package fr.projects.online_checkout.stripe.controller;

import com.stripe.model.Account;
import fr.projects.online_checkout.stripe.service.StripeAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class StripeAccontController {

  private StripeAccountService service;

  public StripeAccontController(StripeAccountService service) {
    this.service = service;
  }

  @GetMapping("/create/{token}")
  public Mono<Account> createAccount(@PathVariable("token") String token) {
    log.info("Token : " + token);
    return service.createStripeAccount("FR", "custom", token);
  }
}

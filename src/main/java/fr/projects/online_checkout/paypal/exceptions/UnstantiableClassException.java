package fr.projects.online_checkout.paypal.exceptions;

import lombok.Getter;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 24/10/2018
 * @Class purposes : .......
 */
@Getter
public class UnstantiableClassException extends Exception {

  private final String message;

  public UnstantiableClassException(String msg) {
    this.message = msg;
  }
}

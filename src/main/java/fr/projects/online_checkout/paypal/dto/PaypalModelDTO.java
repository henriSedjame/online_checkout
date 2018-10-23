package fr.projects.online_checkout.paypal.dto;

import fr.projects.online_checkout.paypal.formatters.JSONFormatters;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
public class PaypalModelDTO {

  public String toJson() {
    return JSONFormatters.toJSON(this);
  }

  @Override
  public String toString() {
    return this.toJson();
  }

}

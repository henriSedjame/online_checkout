package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

@Data
public class PaypalCurrencyDTO extends PaypalModelDTO {
  private String currency;
  private String value;
}

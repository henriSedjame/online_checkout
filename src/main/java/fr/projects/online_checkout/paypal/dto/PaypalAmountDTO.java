package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

@Data
public class PaypalAmountDTO extends PaypalModelDTO {
  private String currency;
  private String total;
  private PaypalDetailsDTO details;
}

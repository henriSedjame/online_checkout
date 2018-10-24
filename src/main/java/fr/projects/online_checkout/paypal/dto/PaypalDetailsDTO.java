package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

@Data
public class PaypalDetailsDTO extends PaypalModelDTO {
  private String subtotal;
  private String shipping;
  private String tax;
  private String handlingFee;
  private String shippingDiscount;
  private String insurance;
  private String giftWrap;
  private String fee;
}

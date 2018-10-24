package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaypalOrderDTO extends PaypalModelDTO {
  private String id;
  private PaypalAmountDTO amount;
  private String paymentMode;
  private String state;
  private String reasonCode;
  private String protectionEligibility;
  private String protectionEligibilityType;
  private String parentPayment;
  private String createTime;
  private String updateTime;
  private List<PaypalLinksDTO> links;
}

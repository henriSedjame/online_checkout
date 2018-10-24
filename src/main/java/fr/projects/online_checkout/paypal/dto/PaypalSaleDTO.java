package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaypalSaleDTO extends PaypalModelDTO {
  private String id;
  private String paymentMode;
  private String state;
  private String reasonCode;
  private String protectionEligibility;
  private String protectionEligibilityType;
  private String clearingTime;
  private String paymentHoldStatus;
  private List<String> paymentHoldReasons;
  private PaypalCurrencyDTO transactionFee;
  private PaypalCurrencyDTO receivableAmount;
  private String parentPayment;
  private String createTime;
  private String updateTime;
}

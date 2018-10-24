package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaypalCaptureDTO extends PaypalModelDTO {
  private String id;
  private PaypalAmountDTO amount;
  private Boolean isFinalCapture;
  private String state;
  private String reasonCode;
  private String parentPayment;
  private String invoiceNumber;
  private PaypalCurrencyDTO transactionFee;
  private String createTime;
  private String updateTime;
  private List<PaypalLinksDTO> links;
}

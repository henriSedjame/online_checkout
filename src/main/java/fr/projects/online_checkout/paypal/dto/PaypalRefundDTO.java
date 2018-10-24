package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaypalRefundDTO extends PaypalModelDTO {
  private String id;
  private PaypalAmountDTO amount;
  private String state;
  private String reason;
  private String invoiceNumber;
  private String saleId;
  private String captureId;
  private String parentPayment;
  private String description;
  private String createTime;
  private String updateTime;
  private String reasonCode;
  private List<PaypalLinksDTO> links;
}

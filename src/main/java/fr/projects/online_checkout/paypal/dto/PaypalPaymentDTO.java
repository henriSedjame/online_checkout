package fr.projects.online_checkout.paypal.dto;


import lombok.Data;

import java.util.List;


@Data
public class PaypalPaymentDTO extends PaypalModelDTO {
  private String id;
  private String intent;
  private String cart;
  private String state;
  private String createTime;
  private String updateTime;
  private String noteToPayer;
  private PaypalPayerDTO payer;
  private PaypalPayeeDTO payee;
  private List<PaypalTransactionDTO> transactions;
  private PaypalRedirectUrlsDTO redirectUrls;
  private List<PaypalLinksDTO> links;

}

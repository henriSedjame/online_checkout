package fr.projects.online_checkout.paypal.dto;

import lombok.Data;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Data
public class PaypalRelatedResourcesDTO extends PaypalModelDTO {
  private PaypalSaleDTO sale;
  private PaypalAuthorizationDTO authorization;
  private PaypalOrderDTO order;
  private PaypalCaptureDTO capture;
  private PaypalRefundDTO refund;
}

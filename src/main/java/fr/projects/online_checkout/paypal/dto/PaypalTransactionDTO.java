package fr.projects.online_checkout.paypal.dto;

import com.paypal.api.payments.Amount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Getter
@Setter
@Builder
public class PaypalTransactionDTO extends PaypalModelDTO {
  private Amount amount;
  private String description;
  private String noteToPayee;
  private String custom;
  private List<PaypalRelatedResourcesDTO> relatedResources;
}

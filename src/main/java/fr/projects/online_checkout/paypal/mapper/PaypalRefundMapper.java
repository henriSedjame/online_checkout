package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Refund;
import fr.projects.online_checkout.paypal.dto.PaypalRefundDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalAmountMapper.class,
        PaypalLinksMapper.class
})
public interface PaypalRefundMapper {
  PaypalRefundMapper INSTANCE = Mappers.getMapper(PaypalRefundMapper.class);

  PaypalRefundDTO toDTO(Refund refund);
}

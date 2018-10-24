package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Order;
import fr.projects.online_checkout.paypal.dto.PaypalOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalAmountMapper.class,
        PaypalLinksMapper.class
})
public interface PaypalOrderMapper {
  PaypalOrderMapper INSTANCE = Mappers.getMapper(PaypalOrderMapper.class);

  PaypalOrderDTO toDTO(Order order);
}

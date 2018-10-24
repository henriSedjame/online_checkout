package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Amount;
import fr.projects.online_checkout.paypal.dto.PaypalAmountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalAmountMapper {
  PaypalAmountMapper INSTANCE = Mappers.getMapper(PaypalAmountMapper.class);

  PaypalAmountDTO toDTO(Amount amount);
}

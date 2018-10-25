package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.Currency;
import fr.projects.online_checkout.paypal.dto.PaypalCurrencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalCurrencyMapper {
  PaypalCurrencyMapper INSTANCE = Mappers.getMapper(PaypalCurrencyMapper.class);

  PaypalCurrencyDTO toDTO(Currency currency);
}

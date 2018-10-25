package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.Phone;
import fr.projects.online_checkout.paypal.dto.PaypalPhoneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalPhoneMapper {
  PaypalPhoneMapper INSTANCE = Mappers.getMapper(PaypalPhoneMapper.class);

  PaypalPhoneDTO toDTO(Phone phone);
}

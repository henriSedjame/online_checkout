package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Details;
import fr.projects.online_checkout.paypal.dto.PaypalDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalDetailsMapper {
  PaypalDetailsMapper INSTANCE = Mappers.getMapper(PaypalDetailsMapper.class);

  PaypalDetailsDTO toDTO(Details details);
}

package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Address;
import fr.projects.online_checkout.paypal.dto.PaypalAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalAddressMapper {
  PaypalAddressMapper INSTANCE = Mappers.getMapper(PaypalAddressMapper.class);

  PaypalAddressDTO toDTO(Address adrdress);
}

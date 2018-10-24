package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Payee;
import fr.projects.online_checkout.paypal.dto.PaypalPayeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalPhoneMapper.class
})
public interface PaypalPayeeMapper {
  PaypalPayeeMapper INSTANCE = Mappers.getMapper(PaypalPayeeMapper.class);

  PaypalPayeeDTO toDTO(Payee payee);
}

package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.Payer;
import fr.projects.online_checkout.paypal.dto.PaypalPayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalPayerInfoMapper.class
})
public interface PaypalPayerMapper {
  PaypalPayerMapper INSTANCE = Mappers.getMapper(PaypalPayerMapper.class);

  PaypalPayerDTO toDTO(Payer payer);
}

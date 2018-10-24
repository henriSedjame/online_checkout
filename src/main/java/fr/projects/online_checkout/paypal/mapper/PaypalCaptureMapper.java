package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Capture;
import fr.projects.online_checkout.paypal.dto.PaypalCaptureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalAmountMapper.class,
        PaypalCurrencyMapper.class,
        PaypalLinksMapper.class
})
public interface PaypalCaptureMapper {
  PaypalCaptureMapper INSTANCE = Mappers.getMapper(PaypalCaptureMapper.class);

  PaypalCaptureDTO toDTO(Capture capture);
}

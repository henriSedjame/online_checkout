package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Sale;
import fr.projects.online_checkout.paypal.dto.PaypalSaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalCurrencyMapper.class
})
public interface PaypalSaleMapper {
  PaypalSaleMapper INSTANCE = Mappers.getMapper(PaypalSaleMapper.class);

  PaypalSaleDTO toDTO(Sale sale);
}

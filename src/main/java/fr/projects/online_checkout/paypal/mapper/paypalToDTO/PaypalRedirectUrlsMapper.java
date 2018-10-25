package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.RedirectUrls;
import fr.projects.online_checkout.paypal.dto.PaypalRedirectUrlsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalRedirectUrlsMapper {
  PaypalRedirectUrlsMapper INSTANCE = Mappers.getMapper(PaypalRedirectUrlsMapper.class);

  PaypalRedirectUrlsDTO toDTO(RedirectUrls redirectUrls);
}

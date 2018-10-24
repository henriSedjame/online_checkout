package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Links;
import fr.projects.online_checkout.paypal.dto.PaypalLinksDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalLinksMapper {
  PaypalLinksMapper INSTANCE = Mappers.getMapper(PaypalLinksMapper.class);

  PaypalLinksDTO toDTO(Links links);
}

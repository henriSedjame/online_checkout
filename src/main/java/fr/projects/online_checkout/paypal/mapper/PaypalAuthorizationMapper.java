package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.Authorization;
import fr.projects.online_checkout.paypal.dto.PaypalAuthorizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalAmountMapper.class,
        PaypalLinksMapper.class
})
public interface PaypalAuthorizationMapper {
  PaypalAuthorizationMapper INSTANCE = Mappers.getMapper(PaypalAuthorizationMapper.class);

  PaypalAuthorizationDTO toDTO(Authorization authorization);
}

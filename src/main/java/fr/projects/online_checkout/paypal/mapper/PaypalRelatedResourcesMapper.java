package fr.projects.online_checkout.paypal.mapper;

import com.paypal.api.payments.RelatedResources;
import fr.projects.online_checkout.paypal.dto.PaypalRelatedResourcesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaypalRelatedResourcesMapper {
  PaypalRelatedResourcesMapper INSTANCE = Mappers.getMapper(PaypalRelatedResourcesMapper.class);

  PaypalRelatedResourcesDTO toDTO(RelatedResources relatedResources);
}

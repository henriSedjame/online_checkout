package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.PayerInfo;
import fr.projects.online_checkout.paypal.dto.PaypalPayerInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
@Mapper(componentModel = "spring", uses = {
        PaypalAddressMapper.class
})
public interface PaypalPayerInfoMapper {
  PaypalPayerInfoMapper INSTANCE = Mappers.getMapper(PaypalPayerInfoMapper.class);

  /**
   * Méthode permettant de convertir un payment paypal en DTO
   *
   * @param payerInfo
   * @return
   */
  PaypalPayerInfoDTO toDTO(PayerInfo payerInfo);
}

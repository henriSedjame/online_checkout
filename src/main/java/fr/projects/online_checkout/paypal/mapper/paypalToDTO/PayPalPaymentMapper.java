package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.paypal.dto.PaypalPaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {
        PaypalPayerMapper.class,
        PaypalPayeeMapper.class,
        PaypalTransactionMapper.class,
        PaypalRedirectUrlsMapper.class,
        PaypalLinksMapper.class
})
public interface PayPalPaymentMapper {
  PayPalPaymentMapper INSTANCE = Mappers.getMapper(PayPalPaymentMapper.class);

  /**
   * Méthode permettant de convertir un payment paypal en DTO
   *
   * @param payment
   * @return
   */
  PaypalPaymentDTO toDTO(Payment payment);
}
package fr.projects.online_checkout.paypal.mapper.paypalToDTO;

import com.paypal.api.payments.Transaction;
import fr.projects.online_checkout.paypal.dto.PaypalTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        PaypalRelatedResourcesMapper.class,
        PaypalAmountMapper.class
})
public interface PaypalTransactionMapper {
  PaypalTransactionMapper INSTANCE = Mappers.getMapper(PaypalTransactionMapper.class);

  PaypalTransactionDTO toDTO(Transaction transaction);
}

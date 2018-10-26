package fr.projects.online_checkout.paypal.mapper.paypalToModel;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.core.model.Paiement;
import org.springframework.stereotype.Component;

@Component
public interface PaiementMapper {

  /**
   * Methode permetant de construire un paiment paypal à partir d'un paiment model
   *
   * @param paiement
   * @return
   */
  Payment toPaypalPayment(Paiement paiement);

}
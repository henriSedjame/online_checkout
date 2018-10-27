package fr.projects.online_checkout.paypal.mapper.paypalToModel;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.core.exceptions.PaypalMerchandEmailMissingException;
import fr.projects.online_checkout.core.model.Paiement;


public interface PaiementMapper {

  /**
   * Methode permetant de construire un paiment paypal à partir d'un paiment model
   *
   * @param paiement
   * @return
   */
  Payment toPaypalPayment(Paiement paiement) throws PaypalMerchandEmailMissingException;

}

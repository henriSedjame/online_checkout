package fr.projects.online_checkout.paypal.mapper.paypalToModel;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.core.exceptions.*;
import fr.projects.online_checkout.core.model.Paiement;

import javax.validation.Valid;


public interface PaiementMapper {

  /**
   * Methode permetant de construire un paiment paypal à partir d'un paiment model
   *
   * @param paiement
   * @return
   */
  Payment toPaypalPayment(@Valid Paiement paiement) throws PaypalMerchandEmailMissingException, ActeursPaiementMissingException, UnExpectedValueException, PanierMissingException, PaiementMotifMissingException;

}

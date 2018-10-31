package fr.projects.online_checkout.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.CountrySpec;
import com.stripe.model.File;
import fr.projects.online_checkout.stripe.exceptions.StripePaymentException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 28/10/2018
 * @Class purposes : .......
 */
public interface StripeAccountService {

  /**
   * Methode permettant de créer un compte stripe
   *
   * @return
   * @throws StripePaymentException
   */
  Mono<Account> createStripeAccount();

  /**
   * Méthode permettant de récupérer un compte client Stripe grâce à l'id du compte
   * @param accountId l'id du compte recherché
   * @return le compte recherché
   * @throws StripePaymentException
   */
  Mono<Account> retrieveAccount(String accountId);

  /**
   * Méthode permettant de mettre à jour un compte stripe
   *
   * @param paramToUpdate la liste des couples (nom, valeur) des paramtres à mettre à jour
   * @return le compte mis à jour
   * @throws StripePaymentException
   */
  Mono<Account> updateAccount(String accountId, Map<String, Object> paramToUpdate);

  /**
   * Methode permettant de mettre à jour un compte stripe grace à un fichier contenant les données
   *
   * @param accountId
   * @param dataFile
   * @return
   */
  Mono<File> updateAccountUsingFile(String accountId, java.io.File dataFile);

  /**
   * Méthode permettant d'enregistrer l'accord des termes du contrat Stripe par la client
   *
   * @param accountId
   * @return
   */
  Mono<Account> acceptServicesAgreement(String accountId);

  CountrySpec getStripeSpecificication(String countryCode) throws StripeException;
}

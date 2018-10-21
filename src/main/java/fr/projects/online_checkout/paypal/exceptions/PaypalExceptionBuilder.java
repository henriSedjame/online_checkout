package fr.projects.online_checkout.paypal.exceptions;

import com.paypal.base.rest.PayPalRESTException;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */

public class PaypalExceptionBuilder<T extends PayPalRESTException>{

  @Getter
  private List<T> exceptions = new ArrayList<>();

  /**
   * Méthode permettant d'ajouter une nouvelle exception au builder
   * @param exception
   */
  public void addException(T exception){
    this.exceptions.add(exception);
  }

  /**
   * Méthode permettant de vider la liste des arguments du builder
   */
  public void clear(){
    this.exceptions.clear();
  }

  /**
   * Méthode permettant de construire une exception avec un message résumant les différentes erreurs de la liste des exceptions
   * @return
   */
  public PayPalRESTException buildException(){

    StringBuilder exceptionMessageBuilder = new StringBuilder();

    // TODO implémenter la méthode de construction de l'exception finale


    this.clear();
    return new PayPalRESTException(exceptionMessageBuilder.toString());
  }

  /**
   * Méthode permettant de lancer l'exception finale
   * @throws PayPalRESTException
   */
  public boolean throwException() throws PayPalRESTException {
    if(this.hasException()) throw this.buildException();
    return false;
  }

  /**
   * Méthode permettant de savoir si le builder a des exceptions
   * @return
   */
  public boolean hasException(){
    return this.exceptions.isEmpty();
  }
}

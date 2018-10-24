package fr.projects.online_checkout.paypal.exceptions;

import com.paypal.base.rest.PayPalRESTException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 21/10/2018
 * @Class purposes : .......
 */

@Slf4j
public class PaypalExceptionBuilder<T extends PayPalRESTException>{

  @Getter
  private List<T> exceptions = new ArrayList<>();
  private Class<T> exceptionClass;

  public PaypalExceptionBuilder(Class<T> exceptionClass) {
    this.exceptionClass = exceptionClass;
  }

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
  public T buildException(){

    T exception = null;

    StringBuilder exceptionMessageBuilder = new StringBuilder();

    // TODO implémenter la méthode de construction de l'exception finale


    try {
      Constructor<T> constructor = exceptionClass.getConstructor(String.class);
      exception = constructor.newInstance(exceptionMessageBuilder.toString());
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage());
    }

    this.clear();

    return exception;
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
    return !this.exceptions.isEmpty();
  }
}

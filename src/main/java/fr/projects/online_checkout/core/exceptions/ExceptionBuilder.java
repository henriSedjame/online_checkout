package fr.projects.online_checkout.core.exceptions;

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
 * @Date 27/10/2018
 * @Class purposes : .......
 */
@Slf4j
public abstract class ExceptionBuilder<T extends Exception> {
  @Getter
  private List<T> exceptions = new ArrayList<>();
  private Class<T> exceptionClass;


  public ExceptionBuilder(Class<T> exceptionClass) {
    this.exceptionClass = exceptionClass;
  }

  /**
   * Méthode permettant d'ajouter une nouvelle exception au builder
   *
   * @param message
   */
  public void addException(String message) {
    T exception = null;
    try {
      Constructor<T> constructor = this.exceptionClass.getConstructor(String.class);
      exception = constructor.newInstance(message);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage());
    }
    this.exceptions.add(exception);
  }

  /**
   * @param message
   * @param throwable
   */
  public void addException(String message, Throwable throwable) {
    T exception = null;
    try {
      Constructor<T> constructor = this.exceptionClass.getConstructor(String.class, Throwable.class);
      exception = constructor.newInstance(message, throwable);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage());
    }
    this.exceptions.add(exception);
  }

  /**
   * Méthode permettant de vider la liste des arguments du builder
   */
  public void clear() {
    this.exceptions.clear();
  }

  /**
   * Méthode permettant de construire une exception avec un message résumant les différentes erreurs de la liste des exceptions
   *
   * @return
   */
  public T buildException() {

    T exception = null;

    StringBuilder exceptionMessageBuilder = new StringBuilder();

    this.exceptions.forEach(e -> {
      exceptionMessageBuilder.append(e.getMessage());
    });

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
   * Méthode permettant de construire une exception avec un message résumant les différentes erreurs de la liste des exceptions
   *
   * @return
   */
  public T buildException(String message, Throwable t) {

    T exception = null;

    try {
      Constructor<T> constructor = exceptionClass.getConstructor(String.class, Throwable.class);
      exception = constructor.newInstance(message, t);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage());
    }

    this.clear();

    return exception;
  }

  /**
   * Méthode permettant de lancer l'exception finale
   *
   * @throws PayPalRESTException
   */
  public boolean throwException() throws T {
    if (this.hasException()) throw this.buildException();
    return false;
  }

  /**
   * Méthode permettant de savoir si le builder a des exceptions
   *
   * @return
   */
  public boolean hasException() {
    return !this.exceptions.isEmpty();
  }

  /**
   * Metode permettant de vérifier qu'il y aucune exception
   *
   * @return
   */
  public boolean isEmpty() {
    return this.exceptions.isEmpty();
  }
}

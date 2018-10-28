package fr.projects.online_checkout.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 27/10/2018
 * @Class purposes : .......
 */
@Slf4j
public class RequireObjects {

  public static <T extends Exception> void requireNotNull(Object object, Class<T> exceptionClass, String message) throws T {
    try {
      Objects.requireNonNull(object);
    } catch (NullPointerException e) {
      try {
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }

  public static <T extends Exception> void expect(Object actualValue, Object expectedValue, Class<T> exceptionClass, String message) throws T {
    if (!Objects.equals(actualValue, expectedValue)) {
      try {
        message = message.concat(" ( la valeur attendue est : " + expectedValue.toString() + ". La valeur actuelle est : " + actualValue.toString());
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }

  public static <T extends Exception, S> void require(S s, Predicate<S> predicate, Class<T> exceptionClass, String message) throws T {
    if (!predicate.test(s)) {
      try {
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }
}

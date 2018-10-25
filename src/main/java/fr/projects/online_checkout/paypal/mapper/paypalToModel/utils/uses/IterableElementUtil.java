package fr.projects.online_checkout.paypal.mapper.paypalToModel.utils.uses;

import fr.projects.online_checkout.paypal.mapper.paypalToModel.utils.annotations.IterableElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 25/10/2018
 * @Class purposes : .......
 */
public class IterableElementUtil {

  @IterableElement
  public <T> List<T> element(T t) {
    List<T> list = new ArrayList<>();
    if (!Objects.isNull(t)) list.add(t);
    return list;
  }
}

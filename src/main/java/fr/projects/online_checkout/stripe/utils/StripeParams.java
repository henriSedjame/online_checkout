package fr.projects.online_checkout.stripe.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
public class StripeParams {

  private static ParamContainer instance;

  public static ParamContainer create() {
    instance = new ParamContainer();
    return instance;
  }

  public static class ParamContainer {

    private Map<String, Object> params = new HashMap<>();

    public ParamContainer put(String key, Object value) {
      instance.params.put(key, value);
      return instance;
    }

    public ParamContainer ifTruePut(Boolean condition, String key, Object value) {
      if (condition) instance.params.put(key, value);
      return instance;
    }

    public Map<String, Object> build() {
      return instance.params;
    }

  }

}

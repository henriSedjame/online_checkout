package fr.projects.online_checkout.paypal.formatters;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 23/10/2018
 * @Class purposes : .......
 */
public class JSONFormatters {

  public static Gson GSON;
  private static FieldNamingPolicy FIELD_NAMING_POLICY;

  static {
    FIELD_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    GSON = (new GsonBuilder()).setPrettyPrinting().setFieldNamingPolicy(FIELD_NAMING_POLICY).create();
  }

  public static <T> String toJSON(T t) {
    return GSON.toJson(t);
  }

}

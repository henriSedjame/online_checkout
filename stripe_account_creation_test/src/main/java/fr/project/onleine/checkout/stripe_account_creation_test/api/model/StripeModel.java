package fr.project.onleine.checkout.stripe_account_creation_test.api.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
public class StripeModel {

  public String toJson() {
    Gson GSON = (new GsonBuilder()).setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    return GSON.toJson(this);
  }
}

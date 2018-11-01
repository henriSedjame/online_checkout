package fr.projects.online_checkout.stripe.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
class StripeParamsTest {

  @Test
  public void create() {
    final StripeParams.ParamContainer params = StripeParams.create();
    assertNotNull(params);
  }

  @Test
  public void put() {
    final Map<String, Object> params = StripeParams.create()
      .put("key", "value")
      .put("key2", "value2")
      .build();
    assertNotNull(params);
    assertEquals(2, params.size());
  }

  @Test
  public void ifTruePut() {
    final Map<String, Object> params = StripeParams.create()
      .put("key", "value")
      .ifTruePut(false, "key2", "value2")
      .build();

    final Map<String, Object> params2 = StripeParams.create()
      .put("key", "value")
      .ifTruePut(true, "key2", "value2")
      .build();

    assertNotNull(params);
    assertEquals(1, params.size());
    assertEquals(2, params2.size());
  }

}

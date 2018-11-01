package fr.project.onleine.checkout.stripe_account_creation_test.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 01/11/2018
 * @Class purposes : .......
 */
@Configuration
public class MessageConverterConfig {
  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return new MappingJackson2HttpMessageConverter(mapper);
  }
}

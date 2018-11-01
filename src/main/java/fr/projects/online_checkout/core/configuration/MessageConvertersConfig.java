package fr.projects.online_checkout.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@EnableAutoConfiguration
public class MessageConvertersConfig {

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return new MappingJackson2HttpMessageConverter(mapper);
  }
}

package bg.example.craftyCocktails.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationBeanConfiguration {

  @Value("${fortuneservice.base.url}")
  private String addressBaseUrl;

  @Bean
  public WebClient webClient() {
    return WebClient.builder().baseUrl(addressBaseUrl).build();
  }

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }
}

package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.dto.AddFortuneDto;
import bg.example.craftyCocktails.model.view.FortunelViewModel;
import java.util.List;
import javax.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FortuneService {

  private final WebClient webClient;

  public FortuneService(WebClient webClient) {
    this.webClient = webClient;
  }

  public List<FortunelViewModel> getAllFortunes() {
    return webClient.get()
        .uri("/fortunes")
        .retrieve()
        .bodyToFlux(new ParameterizedTypeReference<FortunelViewModel>() {
        })
        .collectList()
        .block();
  }

  public void deleteFortuneById(Long fortuneId) {
    webClient.delete()
        .uri("/fortunes/{id}", fortuneId)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public void addFortune(@Valid AddFortuneDto addFortuneDto) {
    webClient.post()
        .uri("/fortunes")
        .bodyValue(addFortuneDto)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }
}

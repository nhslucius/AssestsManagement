package sonnh.dev.assetsmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

    private final WebClient webClient;

    public <T> T getSyn(String url, Class<T> clazz) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(clazz)
                .block(Duration.ofSeconds(5));
    }

    public <T> T postSyn(String url, Object body, Class<T> clazz) {
        return webClient.post()
                .uri(url)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(clazz)
                .block(Duration.ofSeconds(5));
    }
}

package sonnh.dev.assetsmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
public class WebClientConfig {

    private static final Set<String> SENSITIVE_HEADERS = Set.of(
            "authorization", "cookie", "x-api-key"
    );

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    /** Log REQUEST */
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            log.info("➡️ WEBCLIENT REQUEST");
            log.info("METHOD  : {}", request.method());
            log.info("URL     : {}", request.url());
            log.info("HEADERS : {}", maskHeaders(request.headers()));
            return Mono.just(request);
        });
    }

    /** Log RESPONSE (status + headers) */
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            log.info("⬅️ WEBCLIENT RESPONSE");
            log.info("STATUS  : {}", response.statusCode());
            log.info("HEADERS : {}", maskHeaders(response.headers().asHttpHeaders()));
            return Mono.just(response);
        });
    }

    /** Mask sensitive headers */
    private Map<String, List<String>> maskHeaders(HttpHeaders headers) {
        Map<String, List<String>> masked = new HashMap<>();
        headers.forEach((k, v) -> {
            if (SENSITIVE_HEADERS.contains(k.toLowerCase())) {
                masked.put(k, List.of("******"));
            } else {
                masked.put(k, v);
            }
        });
        return masked;
    }
}
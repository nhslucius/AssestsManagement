package sonnh.dev.assetsmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsoupConfig {

    @Bean
    public String defaultUserAgent() {
        return "Mozilla/5.0 (Lucius)";
    }
}
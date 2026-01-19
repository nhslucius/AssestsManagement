package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.dto.StockOverviewDto;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class StockCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${stock.cache.ttl-hours:16}")
    private long ttlHours;

    private static final String KEY_PREFIX = "STOCK_OVERVIEW:";

    public StockOverviewDto get(String stockCode) {
        String key = buildKey(stockCode);
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        // 🔥 FIX QUAN TRỌNG
        return objectMapper.convertValue(value, StockOverviewDto.class);
    }

    public void put(String stockCode, StockOverviewDto dto) {
        String key = buildKey(stockCode);
        redisTemplate.opsForValue().set(
                key,
                dto,
                Duration.ofHours(16)
        );
    }

    private String buildKey(String stockCode) {
        return KEY_PREFIX + stockCode.toUpperCase();
    }
}

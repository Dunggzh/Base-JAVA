package com.its.base_java.other_service.rate_limit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public void resetRateLimit(String clientCode) {
        buckets.remove(clientCode);
    }

    public boolean checkRateLimit(String clientCode, int limitTps) {
        Bucket requestBucket = buckets.computeIfAbsent(clientCode, key -> premiumBucket(limitTps));
        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        return !probe.isConsumed();
    }

    private Bucket premiumBucket(int number) {
        if (number == 0) {
            number = 1000;
        }
        return Bucket.builder()
                .addLimit(Bandwidth.classic(number, Refill.intervally(number, Duration.ofSeconds(1))))
                .build();
    }

}

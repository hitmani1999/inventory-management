package org.eshoping.cache.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheMetricsLogger {
    private final MeterRegistry meterRegistry;

    @Autowired
    public CacheMetricsLogger(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void logCacheMetrics() {
        meterRegistry.get("cache.gets").tags("name", "product").gauge().value();
        double hitCount = meterRegistry.get("cache.hits").tags("name", "product").gauge().value();
        double missCount = meterRegistry.get("cache.misses").tags("name", "product").gauge().value();

        System.out.println("Cache Hit Count: " + hitCount);
        System.out.println("Cache Miss Count: " + missCount);
    }

//    @Scheduled(fixedRate = 6000)
//    public void logMetricsAutomatically() {
//        logCacheMetrics();
//    }
}

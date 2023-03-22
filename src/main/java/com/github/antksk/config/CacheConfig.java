package com.github.antksk.config;

import com.github.antksk.blog.search.service.BlogPopularSearchRankingListener;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.github.antksk.config.KKB_Global_Constants.BLOG_POPULAR_SEARCH_WORD;

@Slf4j
@EnableCaching
@Configuration
class CacheConfig {
    @Value("${cache.maximum-size: 100}")
    private int maximumSize;

    @Value("${cache.duration: 3000}")
    private int duration;

    @Bean
    CaffeineCacheManager caffeineCacheManager(BlogPopularSearchRankingListener listener) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        var caffeine = Caffeine
                .newBuilder()
                .maximumSize(maximumSize)
                // 캐시가 생성된 후, 해당 값이 가장 최근에 대체되거나 마지막으로 읽은 후 특정 기간이 경과하면 캐시에서 자동으로 제거되도록 지정합니다.
                .expireAfterWrite(duration, TimeUnit.MILLISECONDS)
                // 항목이 생성된 후 또는 해당 값을 가장 최근에 바뀐 후 특정 기간이 지나면 각 항목이 캐시에서 자동으로 제거되도록 지정합니다.
                .expireAfterAccess(duration, TimeUnit.MILLISECONDS)
                .removalListener(listener);
        cacheManager.getCache(BLOG_POPULAR_SEARCH_WORD);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}

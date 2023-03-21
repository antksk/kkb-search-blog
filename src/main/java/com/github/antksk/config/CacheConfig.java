package com.github.antksk.config;

import com.github.antksk.blog.search.service.BlogPopularSearchRankingListener;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableCaching
@Configuration
class CacheConfig {
    @Bean
    CaffeineCacheManager caffeineCacheManager(BlogPopularSearchRankingListener listener) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        int maximumSize = CacheType.blogPopularSearchWord.getMaximumSize();
        int duration = CacheType.blogPopularSearchWord.getDuration();
        var caffeine = Caffeine
                .newBuilder()
                .maximumSize(maximumSize)
                // 캐시가 생성된 후, 해당 값이 가장 최근에 대체되거나 마지막으로 읽은 후 특정 기간이 경과하면 캐시에서 자동으로 제거되도록 지정합니다.
                .expireAfterWrite(duration, TimeUnit.MINUTES)
                // 항목이 생성된 후 또는 해당 값을 가장 최근에 바뀐 후 특정 기간이 지나면 각 항목이 캐시에서 자동으로 제거되도록 지정합니다.
                .expireAfterAccess(duration, TimeUnit.MINUTES)
                .removalListener(listener);
        cacheManager.getCache(CacheType.Constants.BLOG_POPULAR_SEARCH_WORD);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}

package com.github.antksk.blog.search.service;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.github.benmanes.caffeine.cache.RemovalCause.EXPIRED;
import static com.github.benmanes.caffeine.cache.RemovalCause.REPLACED;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogPopularSearchRankingListener implements RemovalListener<Object, Object> {
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final Set<RemovalCause> rankingCoverage = Set.of(REPLACED, EXPIRED);
    @Override
    public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
        log.info("## cache remove cause : {}", cause);
        if (rankingCoverage.contains(cause)) {
            applicationEventPublisher.publishEvent(RankingWord.fromCache(key, value));
        }
    }
}

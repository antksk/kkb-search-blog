package com.github.antksk.blog.search.service;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogPopularSearchRankingListener implements RemovalListener<Object, Object> {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
        applicationEventPublisher.publishEvent(RankingWord.fromCache(key,value));
    }
}

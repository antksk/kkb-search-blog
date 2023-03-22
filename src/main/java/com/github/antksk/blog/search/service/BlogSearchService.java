package com.github.antksk.blog.search.service;

import com.github.antksk.blog.search.controller.request.BlogSearchRequest;
import com.github.antksk.blog.search.service.external.BlogSearchApiClient;
import com.github.antksk.blog.search.service.external.BlogSearchResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchService {
    private final ApplicationEventPublisher applicationEventPublisher;

    private final Map<String, BlogSearchApiClient<? extends BlogSearchResults>> mappingBlogSearchApiClient;
    public BlogSearchResults search(BlogSearchRequest blogSearchRequest){
        log.debug("mappingBlogSearchApiClient : {}", mappingBlogSearchApiClient);
        String key = fromBlogSourceToSearchApiClientKey(blogSearchRequest);
        log.info("## mappingBlogSearchApiClient key : {}", key);
        var blogSearchApiClient = mappingBlogSearchApiClient.get(key);
        var blogSearchResults = blogSearchApiClient.search(blogSearchRequest.toParam());

        applicationEventPublisher.publishEvent(blogSearchRequest);
        return blogSearchResults;
    }

    private String fromBlogSourceToSearchApiClientKey(BlogSearchRequest blogSearchRequest) {
        return mappingBlogSearchApiClient.keySet()
                .stream()
                .filter(blogSearchRequest::hasBlogSearchApiClientPackageName)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("BlogSource에서 search api를 찾지 못함"));
    }
}

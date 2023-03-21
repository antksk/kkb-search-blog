package com.github.antksk.blog.search.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.github.antksk.blog.search.controller.request.BlogSearchRequest.DEFAULT_BLOG_SOURCE;
import static com.github.antksk.blog.search.controller.request.GeneralEnum.enumsToMap;

@Slf4j

@RequiredArgsConstructor
enum BlogSource implements GeneralEnum {
    kakao(DEFAULT_BLOG_SOURCE, (r)-> r.toParam("page", "size")),
    naver("naver", (r) -> r.toParam("start", "display"));

    @Getter
    private final String value;
    private final Function<BlogSearchRequest, Map<String, Object>> paramMapper;

    private static final Map<String, BlogSource> mapped = enumsToMap(values(), (t) -> true);

    static BlogSource fromString(String source) {
        return mapped.getOrDefault(source, kakao);
    }


    boolean hasBlogSearchApiClientPackageName(String packageName){
        return Optional.ofNullable(packageName)
                .filter(s->-1 != s.indexOf(value))
                .isPresent();
    }

    Map<String, Object> toParam(BlogSearchRequest blogSearchRequest){
        return paramMapper.apply(blogSearchRequest);
    }
}

package com.github.antksk.blog.search.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Slf4j
@SpringBootTest(properties = {
        "spring.jpa.properties.hibernate.show_sql=true",
        "cache.duration=100"
})
class BlogPopularSearchRankingServiceTest {
    @SpyBean
    @Autowired
    BlogPopularSearchRankingService service;

    @DisplayName("캐시 생성 테스트")
    @Test
    void cacheTest() {
        doNothing().when(service).storedRanking(any());
        List<String> words = List.of("show", "abc", "테스트", "난", "원래", "이번", "블로그는", "감성적이게", "적어보려고", "했다…", "근데", "사람들이", "내", "블로그", "재밋대", "행복의", "기타", "연주");
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int index = random.nextInt(words.size());
            service.incrementWordCount(words.get(index));
        }

        for (var s: words) {
            log.debug("ranking=> word:{}, count:{}", s, service.getCurrentWordCount(s));
        }
    }
}
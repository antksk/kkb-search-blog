package com.github.antksk.blog.search.service;

import com.github.antksk.blog.search.controller.request.BlogSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class BlogSearchEventHandler {
    private final BlogPopularSearchRankingService blogPopularSearchRankingService;

    // 비동기 처리 진행함(추가 설정이 없다면 TaskExecutor로 설정된 기본 빈정보를 가지고 생성된 Thread를 사용함
    @Async
    @EventListener
    public void onBlogSearchRequestEvent(BlogSearchRequest request){
        log.info("## onBlogSearchRequestEvent ##: {}", request);
        blogPopularSearchRankingService.incrementWordCount(request.getQuery());
    }

    @Async
    @EventListener
    public void onBlogPopularSearchRankingWord(RankingWord rankingWord){
        if(rankingWord.isNotEmpty()) {
            log.info("## onBlogPopularSearchRankingWord ##: {}", rankingWord);
            long currentWordCount = blogPopularSearchRankingService.getCurrentWordCount(rankingWord.getWord());
            RankingWord newRankingWord = rankingWord.newRankingWord(currentWordCount);
            blogPopularSearchRankingService.storedRanking(newRankingWord);
            log.info("## db time => word : {}, real count : {}, cc :{}, rc:{}",
                    rankingWord.getWord(), newRankingWord.getCount(), currentWordCount, rankingWord.getCount());
        }
    }
}

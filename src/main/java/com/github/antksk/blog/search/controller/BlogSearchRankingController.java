package com.github.antksk.blog.search.controller;

import com.github.antksk.blog.search.service.BlogPopularSearchRankingService;
import com.github.antksk.blog.search.service.RankingWord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
class BlogSearchRankingController {

    private final BlogPopularSearchRankingService blogPopularSearchRankingService;

    @GetMapping("/blog/search/keyword/ranking")
    List<RankingWord> ranking(){
        return blogPopularSearchRankingService.getRankingWords();
    }
}

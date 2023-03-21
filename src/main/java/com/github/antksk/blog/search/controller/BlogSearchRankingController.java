package com.github.antksk.blog.search.controller;

import com.github.antksk.blog.search.controller.request.BlogSearchRequest;
import com.github.antksk.blog.search.service.BlogPopularSearchRankingService;
import com.github.antksk.blog.search.service.BlogSearchService;
import com.github.antksk.blog.search.service.RankingWord;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import com.github.antksk.blog.search.service.external.BlogSearchResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

import java.util.List;

import static com.github.antksk.blog.search.controller.request.BlogSearchRequest.DEFAULT_BLOG_SEARCH_SORT_MODE;
import static com.github.antksk.blog.search.controller.request.BlogSearchRequest.DEFAULT_BLOG_SOURCE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

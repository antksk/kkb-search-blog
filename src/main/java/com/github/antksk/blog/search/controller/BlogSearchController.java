package com.github.antksk.blog.search.controller;

import com.github.antksk.blog.search.service.BlogSearchService;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

import static com.github.antksk.blog.search.controller.request.BlogSearchRequest.fromRequest;
import static com.github.antksk.config.KKB_Global_Constants.DEFAULT_BLOG_SEARCH_SORT_MODE;
import static com.github.antksk.config.KKB_Global_Constants.DEFAULT_BLOG_SOURCE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping("/blog/search")
    Page<? extends BlogSearchResult> search(
            @RequestParam @Size(min = 1,max = 10) String query,
            @RequestParam(defaultValue = DEFAULT_BLOG_SOURCE, required = false) String source,
            @PageableDefault(page = 1, sort = DEFAULT_BLOG_SEARCH_SORT_MODE) Pageable pageable){

        log.debug("query : {}", query);
        log.debug("source : {}", source);
        log.debug("pageable : {}", pageable);
        var blogSearchRequest = fromRequest(query, source, pageable);

        var blogSearchResults = blogSearchService.search(blogSearchRequest);

        return blogSearchResults.getPageableResults(pageable);
    }
}

package com.github.antksk.blog.search.service;

import com.github.antksk.blog.search.repository.BlogSearchKeywordRepository;
import com.github.antksk.blog.search.repository.BlogSearchWordEntity;
import com.github.antksk.config.CacheType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogPopularSearchRankingService {
    private final CacheManager cacheManager;

    private final BlogSearchKeywordRepository blogSearchKeywordRepository;

    private Cache getWordCache(){
        var cacheName = CacheType.Constants.BLOG_POPULAR_SEARCH_WORD;
        return cacheManager.getCache(cacheName);
    }

    long getCurrentWordCount(String word){
        Cache cacheWord = getWordCache();
        return Optional.ofNullable(cacheWord.get(word, Long.class))
                .orElse(0L);
    }

    long incrementWordCount(String word){
        Long count = getCurrentWordCount(word);
        getWordCache().put(word, ++count);
        log.debug("word : {}, count: {}", word, count);
        return count;
    }

    @Transactional
    void storedRanking(RankingWord newRankingWord) {

        String word = newRankingWord.getWord();
        long count = newRankingWord.getCount();

        BlogSearchWordEntity wordEntity = Optional.ofNullable(blogSearchKeywordRepository.findByWord(word))
                .orElse(BlogSearchWordEntity.builder()
                        .word(word)
                        .count(count)
                        .build());

        wordEntity.modifyCount(count);

        blogSearchKeywordRepository.save(wordEntity);
    }

    @Transactional(readOnly = true)
    public List<RankingWord> getRankingWords(){
        return blogSearchKeywordRepository.findTop10By(Sort.by(Sort.Order.desc("count"), Sort.Order.desc("word")))
                .stream()
                .map(e->RankingWord.from(e.getWord(), e.getCount()))
                .collect(toUnmodifiableList());
    }




}

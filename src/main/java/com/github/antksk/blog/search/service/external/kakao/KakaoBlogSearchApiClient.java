package com.github.antksk.blog.search.service.external.kakao;

import com.github.antksk.blog.search.service.external.BlogSearchApiClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

import static com.github.antksk.config.KKB_Global_Constants.DEFAULT_BLOG_SOURCE;

@FeignClient(
        name = "kakaoBlogSearchApiClient",
        url = "${openApi.kakao.baseUri}",
        contextId = DEFAULT_BLOG_SOURCE
)
interface KakaoBlogSearchApiClient extends BlogSearchApiClient<KakaoBlogSearchResults> {

    @GetMapping(path="${openApi.kakao.searchBlogUrl}", headers = "Authorization=${openApi.kakao.auth}")
    KakaoBlogSearchResults search(
            @SpringQueryMap Map<String,Object> query);

}

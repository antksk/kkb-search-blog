package com.github.antksk.blog.search.service.external.naver;

import com.github.antksk.blog.search.service.external.BlogSearchApiClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(
        name = "naverBlogSearchApiClient",
        url = "${openApi.naver.baseUri}",
        contextId = "naver",
        primary = false
)
interface NaverBlogSearchApiClient extends BlogSearchApiClient<NaverBlogSearchResults> {

    @GetMapping(path="${openApi.naver.searchBlogUrl}",
            headers = {
                "X-Naver-Client-Id=${openApi.naver.auth.id}",
                "X-Naver-Client-Secret=${openApi.naver.auth.secret}"
    })
    NaverBlogSearchResults search(@SpringQueryMap Map<String,Object> query);
}

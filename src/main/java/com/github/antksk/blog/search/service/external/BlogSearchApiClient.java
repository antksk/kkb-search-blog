package com.github.antksk.blog.search.service.external;

import java.util.Map;

public interface BlogSearchApiClient<T extends BlogSearchResults> {
    BlogSearchResults search(Map<String,Object> query);
}

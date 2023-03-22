package com.github.antksk.blog.search.controller.request;

import com.github.antksk.config.KKB_Global_Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Map;

import static com.github.antksk.blog.search.controller.request.BlogSearchRequest.fromRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.PageRequest.ofSize;

@DisplayName("블로그 검색 연동시 여러 외부 api연동 할 수 있도록 구성")
class BlogSearchRequestTest {

    @DisplayName("blogSource값으로 들어 올 수 있는 정보는 현재로 kakao, naver키워드 임")
    @Test
    void hasBlogSource() {
        assertTest(true, "kakao", "com.github.antksk.blog.search.service.external.kakao.KakaoBlogSearchApiClient");
        assertTest(true, "naver", "com.github.antksk.blog.search.service.external.naver.NaverBlogSearchApiClient");
        assertTest(false, "naver", "com.github.antksk.blog.search.service.external.BlogSearchApiClient");
    }

    private void assertTest(boolean result, String source, String packageName) {
        assertEquals(result, fromRequest("test", source, ofSize(1))
                .hasBlogSearchApiClientPackageName(packageName));
    }

    @DisplayName("blogSource값을 기준으로 api 파라미터 매칭됨")
    @Test
    void param() {
        String query = "test";
        int page = 1;
        int size = 10;
        /**
         * [KAKAO Parameter 정보]
         * query	String	검색을 원하는 질의어	O
         * sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy	X
         * page	Integer	결과 페이지 번호, 1~50 사이의 값, 기본 값 1	X
         * size	Integer	한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10	X
         */
        assertRequestEqOpenApiParam("kakao", "page", "size", query, page, size, KKB_Global_Constants.DEFAULT_BLOG_SEARCH_SORT_MODE);
        /**
         * [NAVER Parameter 정보]
         * query	String	Y	검색어. UTF-8로 인코딩되어야 합니다.
         * display	Integer	N	한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
         * start	Integer	N	검색 시작 위치(기본값: 1, 최댓값: 1000)
         * sort	String	N	검색 결과 정렬 방법
         * - sim: 정확도순으로 내림차순 정렬(기본값)
         * - date: 날짜순으로 내림차순 정렬
         */
        assertRequestEqOpenApiParam("naver", "start", "display", query, page, size, "sim");
    }

    private void assertRequestEqOpenApiParam(String source, String currentPageKey, String displayResultSizeKey, String query, int page, int size, String sortMode) {
        var openApiParam = Map.of(
                "query", query,
                currentPageKey, page,
                displayResultSizeKey, size,
                "sort", sortMode);

        Map<String, Object> requestToApiParam = fromRequest(query, source, PageRequest.of(page, size, Sort.by(Sort.Order.by(sortMode))))
                .toParam();
        assertEquals(openApiParam, requestToApiParam);
    }


}
package com.github.antksk.blog.search.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.antksk.blog.search.controller.RestDocsUtils.json;
import static com.github.antksk.blog.search.controller.RestDocsUtils.parameter;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BlogSearchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void search() throws Exception {

        var request = get("/blog/search")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("query", "test")
                        .param("source", "kakao")
                        .param("page", "1")
                        .param("size", "5")
                ;

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("blog-search",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        , requestParameters(
                                parameter("query", "[Required] 검색을 원하는 질의어 (최대 10자 까지 입력 가능)")
                                , parameter(true, "page", "[Optional] 결과 페이지 번호")
                                , parameter(true, "size", "[Optional] 한 페이지에 보여질 문서 수 (최대 50개)")
                                , parameter(true, "sort", "[Optional] 결과 문서 정렬 방식 (accuracy(정확도순) or recency(최신순), 기본값 : accuracy)")
                                , parameter(true, "source", "[Optional] 블로그 API 대상(kakao(카카오) or naver(네이버), 기본값 : kakao)")
                        )
                        , responseFields(RestDocsUtils.fields(
                                  content()
                                , pageable()
                                , sort()
                           )
                        )
                    )
                );
    }

    private static List<FieldDescriptor> content(){
        return List.of(
                json(ARRAY, "content", "결과코드")
                , json(STRING, "content.[].name", "블로그 이름")
                , json(STRING, "content.[].url", "블로그 post url")
                , json(STRING, "content.[].contents", "문서 본문 중 일부")
                , json(STRING, "content.[].title", "문서 제목")
                , json(STRING, "content.[].postDate", "문서 글 작성시간(yyyy.MM.dd)")
        );
    }

    private static List<FieldDescriptor> pageable(){
        return List.of(
                json(OBJECT, "pageable", "페이지 정보")
                , json(OBJECT, "pageable.sort", "페이지 정보")
                    , json(BOOLEAN, "pageable.sort.sorted", "페이지 정보")
                    , json(BOOLEAN, "pageable.sort.unsorted", "페이지 정보")
                    , json(BOOLEAN, "pageable.sort.empty", "페이지 정보")
                , json(NUMBER, "pageable.pageNumber", "페이지 정보")
                , json(NUMBER, "pageable.pageSize", "페이지 정보")
                , json(NUMBER, "pageable.offset", "페이지 크기에 따라 상대적 오프셋")
                , json(BOOLEAN, "pageable.paged", "페이지 정보")
                , json(BOOLEAN, "pageable.unpaged", "페이지 정보")

                , json(NUMBER, "totalPages", "전체 페이지")
                , json(NUMBER, "totalElements", "전체 요소")
                , json(BOOLEAN, "last", "마지막 페이지 여부")
                , json(NUMBER, "numberOfElements", "페이지 정보")
                , json(NUMBER, "size", "한 페이지에서 보여줄 사이즈의 갯수")
                , json(NUMBER, "number", "페이지 정보")
                , json(BOOLEAN, "first", "첫 패이지 여부")
                , json(BOOLEAN, "empty", "리스트가 비어 있는지 여부")
        );
    }

    static List<FieldDescriptor> sort(){
        return List.of(
                json(OBJECT, "sort", "페이지 정보")
                , json(BOOLEAN, "sort.sorted", "페이지 정보")
                , json(BOOLEAN, "sort.unsorted", "페이지 정보")
                , json(BOOLEAN, "sort.empty", "페이지 정보")
        );
    }



}
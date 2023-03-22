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

import static com.github.antksk.blog.search.controller.RestDocsUtils.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BlogSearchRankingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void ranking() throws Exception {

        var request = get("/blog/search/keyword/ranking")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("blog-ranking",
                        preprocessResponse(prettyPrint())
                        , responseFields(fields(
                                  content()
                           )
                        )
                    )
                );
    }

    private static List<FieldDescriptor> content(){
        return List.of(
                  json(STRING, "[].word", "인기 검색어 키워드")
                , json(NUMBER, "[]count", "검색 횟수")
        );
    }



}
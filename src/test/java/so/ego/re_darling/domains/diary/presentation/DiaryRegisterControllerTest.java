package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.diary.application.DiaryRegisterService;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(DiaryRegisterController.class)
class DiaryRegisterControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private DiaryRegisterService diaryRegisterService;

  @Test
  void registerDiary() throws Exception {

    // given
    final DiaryRegisterRequest diaryRegisterRequest =
        DiaryRegisterRequest.builder()
            .date(LocalDateTime.now())
            .coupleToken("AAAAA")
            .socialToken("abc")
            .say(" ")
            .build();

    final DiaryRegisterResponse diaryRegisterResponse =
        DiaryRegisterResponse.builder().diaryId(1L).build();

    given(diaryRegisterService.addDiary(diaryRegisterRequest)).willReturn(diaryRegisterResponse);

    this.mockMvc
        .perform(
            post("/diary")
                .content(this.objectMapper.writeValueAsString(diaryRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "diary-add",
                requestFields(
                    fieldWithPath("date").description("다이어리 기록할 날짜"),
                    fieldWithPath("coupleToken").description("커플 토큰"),
                    fieldWithPath("socialToken").description("소셜 토큰"),
                    fieldWithPath("say").description("다이어리 댓글 (삭제 예정)")),
                responseFields(fieldWithPath("diaryId").description("다이어리 Index"))));
  }

  @Test
  void addDiaryPlaces() throws Exception {
    final List<DiaryPlaceRegisterRequest> diaryPlaceRegisterRequest =
        Lists.newArrayList(
            DiaryPlaceRegisterRequest.builder().diaryId(1L).comment("역시 이뻐").name("석촌호수").build(),
            DiaryPlaceRegisterRequest.builder()
                .diaryId(1L)
                .comment("여기 맛있어")
                .name("송리단길 맛집")
                .build());

    // when
    diaryRegisterService.addDiaryPlaces(diaryPlaceRegisterRequest);

    mockMvc
        .perform(
            post("/diary/places")
                .content(this.objectMapper.writeValueAsString(diaryPlaceRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(
            document(
                "diary-place-add",
                requestFields(
                    fieldWithPath("[].diaryId").description("다이어리 Index"),
                    fieldWithPath("[].name").description("다이어리 장소 이름"),
                    fieldWithPath("[].comment").description("다이어리 장소 댓글"))));
  }
}

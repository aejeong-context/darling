package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.diary.application.dto.DiaryUpdateRequest;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryComment;
import so.ego.re_darling.domains.diary.domain.DiaryCommentRepository;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class DiaryUpdateControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;
  @Autowired private DiaryRepository diaryRepository;
  @Autowired private DiaryCommentRepository diaryCommentRepository;

  @Test
  void updateDiaryComment() throws Exception {

    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
    User user = userRepository.save(User.builder().couple(couple).socialToken("abc").build());
    userRepository.save(User.builder().couple(couple).socialToken("def").build());

    Diary diary =
        diaryRepository.save(Diary.builder().couple(couple).date(LocalDateTime.now()).build());

    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("여기 별로야").user(user).build());

    final DiaryUpdateRequest diaryUpdateRequest =
        DiaryUpdateRequest.builder()
            .diaryId(diary.getId())
            .say("여기 좋았어")
            .socialToken("abc")
            .build();

    this.mockMvc
        .perform(
            put("/diary")
                .content(this.objectMapper.writeValueAsString(diaryUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "diary-comment-update",
                requestFields(
                    fieldWithPath("diaryId").description("다이어리 Index"),
                    fieldWithPath("say").description("다이어리 댓글"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }
}

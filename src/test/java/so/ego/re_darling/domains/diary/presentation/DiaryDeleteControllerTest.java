package so.ego.re_darling.domains.diary.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.diary.domain.*;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class DiaryDeleteControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;
  @Autowired private DiaryRepository diaryRepository;
  @Autowired private DiaryCommentRepository diaryCommentRepository;
  @Autowired private DiaryPlaceRepository diaryPlaceRepository;

  @BeforeEach
  void setUp() {
    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
    User user = userRepository.save(User.builder().couple(couple).socialToken("abc").build());
    userRepository.save(User.builder().couple(couple).socialToken("def").build());

    Diary diary =
        diaryRepository.save(Diary.builder().couple(couple).date(LocalDateTime.now()).build());

    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("여기 별로야").user(user).build());
    diaryPlaceRepository.save(
        DiaryPlace.builder().diary(diary).title("한강").comment("오늘 바람 많이 불었어...").build());
  }

  @Test
  void 다이어리_삭제() throws Exception {
    this.mockMvc
        .perform(delete("/diary/{diaryId}", 1L))
        .andExpect(status().isOk())
        .andDo(
            document(
                "diary-delete",
                pathParameters(parameterWithName("diaryId").description("다이어리 Index"))));
  }

  @Test
  void 다이어리_장소_삭제() throws Exception {
    this.mockMvc
        .perform(delete("/diary/place/{diaryPlaceId}", 1L))
        .andExpect(status().isOk())
        .andDo(
            document(
                "diary-place-delete",
                pathParameters(parameterWithName("diaryPlaceId").description("다이어리 장소 Index"))));
  }
}

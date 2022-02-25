package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.diary.application.DiaryUpdateService;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceUpdateRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryUpdateRequest;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
//@SpringBootTest
  @WebMvcTest(DiaryUpdateController.class)
class DiaryUpdateControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

//  @Autowired private UserRepository userRepository;
//  @Autowired private CoupleRepository coupleRepository;
//  @Autowired private DiaryRepository diaryRepository;
//  @Autowired private DiaryCommentRepository diaryCommentRepository;
//  @Autowired private DiaryPlaceRepository diaryPlaceRepository;
//
//  @BeforeEach
//  void setUp() {
//    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
//    User user = userRepository.save(User.builder().couple(couple).socialToken("abc").build());
//    userRepository.save(User.builder().couple(couple).socialToken("def").build());
//
//    Diary diary =
//        diaryRepository.save(Diary.builder().couple(couple).date(LocalDateTime.now()).build());
//
//    diaryCommentRepository.save(
//        DiaryComment.builder().diary(diary).comment("여기 별로야").user(user).build());
//    diaryPlaceRepository.save(
//        DiaryPlace.builder().diary(diary).title("한강").comment("오늘 바람 많이 불었어...").build());
//  }

  @MockBean
  DiaryUpdateService diaryUpdateService;

  @Test
  void updateDiaryComment() throws Exception {

    final DiaryUpdateRequest diaryUpdateRequest =
        DiaryUpdateRequest.builder().diaryId(1L).say("여기 좋았어").socialToken("abc").build();

    given(diaryUpdateService.updateDiaryComment(diaryUpdateRequest)).willReturn(1L);

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

  @Test
  void updateDiaryPlace() throws Exception {

    final DiaryPlaceUpdateRequest diaryPlaceUpdateRequest =
        DiaryPlaceUpdateRequest.builder()
            .placeId(1L)
            .name("배곧 한울공원")
            .comment("사람없어서 진짜 좋다")
            .build();

    given(diaryUpdateService.updateDiaryPlace(diaryPlaceUpdateRequest)).willReturn(1L);

    this.mockMvc
        .perform(
            put("/diary/place")
                .content(this.objectMapper.writeValueAsString(diaryPlaceUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "diary-place-update",
                requestFields(
                    fieldWithPath("placeId").description("장소 Index"),
                    fieldWithPath("name").description("장소 이름"),
                    fieldWithPath("comment").description("장소 설명"))));

  }
}

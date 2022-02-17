package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.ObjectMessage;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class DiaryRegisterControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;
  @Autowired private DiaryRepository diaryRepository;

  @BeforeEach
  void setup() {
    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
    userRepository.save(User.builder().socialToken("abc").couple(couple).build());
    userRepository.save(User.builder().socialToken("def").couple(couple).build());
    diaryRepository.save(Diary.builder().couple(couple).date(LocalDateTime.now()).build());
  }

  @Test
  void registerDiary() throws Exception {

    final DiaryRegisterRequest diaryRegisterRequest =
        DiaryRegisterRequest.builder()
            .date(LocalDateTime.now())
            .coupleToken("AAAAA")
            .socialToken("abc")
            .say(" ")
            .build();
    mockMvc
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
    mockMvc
        .perform(
            post("/diary/places")
                .content(this.objectMapper.writeValueAsString(diaryPlaceRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "diary-place-add",
                requestFields(
                    fieldWithPath("[].diaryId").description("다이어리 Index"),
                    fieldWithPath("[].name").description("다이어리 장소 이름"),
                    fieldWithPath("[].comment").description("다이어리 장소 댓글"))));
  }
}

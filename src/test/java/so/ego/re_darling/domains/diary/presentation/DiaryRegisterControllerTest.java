package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.ObjectMessage;
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
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import java.time.LocalDateTime;

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

  @Test
  void registerDiary() throws Exception {
    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
    userRepository.save(User.builder().socialToken("abc").couple(couple).build());
    userRepository.save(User.builder().socialToken("def").couple(couple).build());

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
}

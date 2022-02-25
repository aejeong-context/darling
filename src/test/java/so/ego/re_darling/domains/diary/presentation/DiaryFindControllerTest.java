package so.ego.re_darling.domains.diary.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.diary.application.DiaryFindService;
import so.ego.re_darling.domains.diary.application.dto.DiaryFindAllResponse;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceFindAllResponse;
import so.ego.re_darling.domains.diary.domain.*;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class DiaryFindControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;
  @Autowired private DiaryRepository diaryRepository;

  @Autowired private DiaryPlaceRepository diaryPlaceRepository;
  @Autowired private DiaryCommentRepository diaryCommentRepository;

  @MockBean private DiaryFindService diaryFindService;

  @BeforeEach
  void setUp() {
    Couple couple =
        coupleRepository.save(
            Couple.builder()
                .coupleToken("AAAAA")
                .firstDay(LocalDateTime.now().minusDays(3))
                .build());
    User user = userRepository.save(User.builder().socialToken("abc").couple(couple).build());
    User partner = userRepository.save(User.builder().socialToken("def").couple(couple).build());
    Diary diary =
        diaryRepository.save(Diary.builder().couple(couple).date(LocalDateTime.now()).build());
    diaryPlaceRepository.save(
        DiaryPlace.builder().diary(diary).comment("진짜 너무 추운거 아닌가").title("한강").build());
    diaryPlaceRepository.save(
        DiaryPlace.builder().diary(diary).comment("피자는 피자헛이다><").title("피자헛").build());
    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("오늘은 그냥 다 추움").user(user).build());
    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("오늘 너무 시원했어 ~").user(partner).build());
  }

  @Test
  void findAllDiary() throws Exception {

    List<DiaryPlaceFindAllResponse> diaryPlaceFindAllResponseList =
        Lists.list(DiaryPlaceFindAllResponse.builder().diaryPlaceId(1L).name("한강").comment("이쁘다").build());
    List<DiaryFindAllResponse> diaryFindAllResponse =
        Lists.list(
            DiaryFindAllResponse.builder()
                .diaryId(1L)
                .datingDate("900")
                .date("2019년8월24일")
                .diaryPlaceNames("#한강")
                .mySay("추운날이었다")
                .yourSay("나름 추운날이군.")
                .DiaryPlaceList(diaryPlaceFindAllResponseList)
                .build());

    given(diaryFindService.findAllDiary("abc", "AAAAA")).willReturn(diaryFindAllResponse);
    mockMvc
        .perform(get("/diarys/{socialToken}/{coupleToken}", "abc", "AAAAA"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "diary-find-all",
                pathParameters(
                    parameterWithName("socialToken").description("소셜 토큰"),
                    parameterWithName("coupleToken").description("커플 토큰")),
                responseFields(
                    fieldWithPath("[].diaryId").description("다이어리 Index"),
                    fieldWithPath("[].datingDate").description("만날 날짜 수"),
                    fieldWithPath("[].date").description("다이어리 날짜"),
                    fieldWithPath("[].diaryPlaceNames").description("장소 태그"),
                    fieldWithPath("[].diaryPlaceList[].diaryPlaceId").description("장소 Index"),
                    fieldWithPath("[].diaryPlaceList[].name").description("장소 이름"),
                    fieldWithPath("[].diaryPlaceList[].comment").description("장소에 대한 댓글"),
                    fieldWithPath("[].mySay").description("다이어리 사용자 댓글"),
                    fieldWithPath("[].yourSay").description("다이어리 상대방 댓글"))));
  }
}

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
        DiaryPlace.builder().diary(diary).comment("?????? ?????? ????????? ?????????").title("??????").build());
    diaryPlaceRepository.save(
        DiaryPlace.builder().diary(diary).comment("????????? ???????????????><").title("?????????").build());
    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("????????? ?????? ??? ??????").user(user).build());
    diaryCommentRepository.save(
        DiaryComment.builder().diary(diary).comment("?????? ?????? ???????????? ~").user(partner).build());
  }

  @Test
  void findAllDiary() throws Exception {

    List<DiaryPlaceFindAllResponse> diaryPlaceFindAllResponseList =
        Lists.list(DiaryPlaceFindAllResponse.builder().diaryPlaceId(1L).name("??????").comment("?????????").build());
    List<DiaryFindAllResponse> diaryFindAllResponse =
        Lists.list(
            DiaryFindAllResponse.builder()
                .diaryId(1L)
                .datingDate("900")
                .date("2019???8???24???")
                .diaryPlaceNames("#??????")
                .mySay("??????????????????")
                .yourSay("?????? ???????????????.")
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
                    parameterWithName("socialToken").description("?????? ??????"),
                    parameterWithName("coupleToken").description("?????? ??????")),
                responseFields(
                    fieldWithPath("[].diaryId").description("???????????? Index"),
                    fieldWithPath("[].datingDate").description("?????? ?????? ???"),
                    fieldWithPath("[].date").description("???????????? ??????"),
                    fieldWithPath("[].diaryPlaceNames").description("?????? ??????"),
                    fieldWithPath("[].diaryPlaceList[].diaryPlaceId").description("?????? Index"),
                    fieldWithPath("[].diaryPlaceList[].name").description("?????? ??????"),
                    fieldWithPath("[].diaryPlaceList[].comment").description("????????? ?????? ??????"),
                    fieldWithPath("[].mySay").description("???????????? ????????? ??????"),
                    fieldWithPath("[].yourSay").description("???????????? ????????? ??????"))));
  }
}

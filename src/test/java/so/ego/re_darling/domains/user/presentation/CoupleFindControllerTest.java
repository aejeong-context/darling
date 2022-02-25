package so.ego.re_darling.domains.user.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.user.application.CoupleFindService;
import so.ego.re_darling.domains.user.application.dto.CoupleCheckResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleFindResponse;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CoupleFindController.class)
class CoupleFindControllerTest {

  private MockMvc mockMvc;

  @MockBean private CoupleFindService coupleFindService;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  void findCouple() throws Exception {

    final CoupleFindResponse coupleFindResponse =
        CoupleFindResponse.builder()
            .coupleToken("AAAAA")
            .specialDay("2019년08월24일")
            .nickname1("애정쓰")
            .nickname2("지수쓰")
            .meetingDate(900)
            .background_path("aaa")
            .user1_profile_path("http://profiled_pat_1")
            .user2_profile_path("http://profiled_pat_2")
            .sayMe("오늘도 힘내자")
            .sayYou("너도 힘내!")
            .build();

    when(coupleFindService.findCouple("AAAAA", "abc")).thenReturn(coupleFindResponse);

    mockMvc
        .perform(get("/couple/main/{coupleToken}/{socialToken}", "AAAAA", "abc"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "couple-find",
                pathParameters(
                    parameterWithName("coupleToken").description("커플 토큰"),
                    parameterWithName("socialToken").description("소셜 토큰")),
                responseFields(
                    fieldWithPath("coupleToken").description("커플 토큰"),
                    fieldWithPath("specialDay").description("만날 날짜"),
                    fieldWithPath("nickname1").description("본인 닉네임"),
                    fieldWithPath("nickname2").description("상대방 닉네임"),
                    fieldWithPath("meetingDate").description("만날 날 수"),
                    fieldWithPath("background_path").description("배경 사진 url"),
                    fieldWithPath("user1_profile_path").description("본인 프로필 사진 url"),
                    fieldWithPath("user2_profile_path").description("상대방 프로필 사진 url"),
                    fieldWithPath("sayMe").description("나의 상태 메시지"),
                    fieldWithPath("sayYou").description("상대방 상태 메시지"))));
  }

  @Test
  void coupleConnectCheck() throws Exception {

    final CoupleCheckResponse coupleCheckResponse =
        CoupleCheckResponse.builder().result(true).build();

    when(coupleFindService.coupleCheck("abc")).thenReturn(coupleCheckResponse);

    this.mockMvc
        .perform(get("/couple/check/{socialToken}", "abc"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "couple-find-check",
                pathParameters(parameterWithName("socialToken").description("소셜 토큰")),
                responseFields(fieldWithPath("result").description("연결상태 유무"))));
  }
}

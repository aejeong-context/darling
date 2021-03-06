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
            .specialDay("2019???08???24???")
            .nickname1("?????????")
            .nickname2("?????????")
            .meetingDate(900)
            .background_path("aaa")
            .user1_profile_path("http://profiled_pat_1")
            .user2_profile_path("http://profiled_pat_2")
            .sayMe("????????? ?????????")
            .sayYou("?????? ??????!")
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
                    parameterWithName("coupleToken").description("?????? ??????"),
                    parameterWithName("socialToken").description("?????? ??????")),
                responseFields(
                    fieldWithPath("coupleToken").description("?????? ??????"),
                    fieldWithPath("specialDay").description("?????? ??????"),
                    fieldWithPath("nickname1").description("?????? ?????????"),
                    fieldWithPath("nickname2").description("????????? ?????????"),
                    fieldWithPath("meetingDate").description("?????? ??? ???"),
                    fieldWithPath("background_path").description("?????? ?????? url"),
                    fieldWithPath("user1_profile_path").description("?????? ????????? ?????? url"),
                    fieldWithPath("user2_profile_path").description("????????? ????????? ?????? url"),
                    fieldWithPath("sayMe").description("?????? ?????? ?????????"),
                    fieldWithPath("sayYou").description("????????? ?????? ?????????"))));
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
                pathParameters(parameterWithName("socialToken").description("?????? ??????")),
                responseFields(fieldWithPath("result").description("???????????? ??????"))));
  }
}

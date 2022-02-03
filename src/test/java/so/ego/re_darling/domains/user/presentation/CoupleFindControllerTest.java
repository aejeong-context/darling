package so.ego.re_darling.domains.user.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.user.application.CoupleFindService;
import so.ego.re_darling.domains.user.application.UserRegisterService;
import so.ego.re_darling.domains.user.application.dto.CoupleCheckResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleFindResponse;
import so.ego.re_darling.domains.user.application.dto.UserRegisterRequest;
import so.ego.re_darling.domains.user.application.dto.UserRegisterResponse;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class CoupleFindControllerTest {

  private MockMvc mockMvc;
  @MockBean private UserRegisterService userRegisterService;
  @MockBean private CoupleFindService coupleFindService;
  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  // TODO: 2022/02/01 ResponseField 오류
  @Transactional
  @Test
  void findCouple() throws Exception {

    userRepository.save(User.builder().socialToken("abc").build());
    User user = userRepository.save(User.builder().socialToken("edf").build());

    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());

    user.updateCouple(couple);

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
        .andDo(
            document("couple-find-check",
                pathParameters(parameterWithName("socialToken").description("소셜 토큰")),
                responseFields(fieldWithPath("result").description("연결상태 유무"))));
  }
}

package so.ego.re_darling.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.user.application.UserFindService;
import so.ego.re_darling.domains.user.application.dto.UserFindResponse;
import so.ego.re_darling.domains.user.application.dto.UserLoginRequest;
import so.ego.re_darling.domains.user.application.dto.UserLoginResponse;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
// @SpringBootTest
@WebMvcTest(UserFindController.class)
class UserFindControllerTest {
  private MockMvc mockMvc;

  @MockBean private UserFindService userFindService;
  @Autowired protected ObjectMapper objectMapper;

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
  void checkUser() throws Exception {
    final UserFindResponse userFindResponse = UserFindResponse.builder().result(true).build();
    when(userFindService.findUser("abc")).thenReturn(userFindResponse);

    this.mockMvc
        .perform(get("/user/check/{socialToken}", "abc").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "user-find-check",
                pathParameters(parameterWithName("socialToken").description("소셜토큰")),
                responseFields(fieldWithPath("result").description("회원 조회 결과"))));
  }
  // TODO: 2022/02/01 ResponseField 오류
  @Test
  void loginUser() throws Exception {

    // given

    final UserLoginRequest userLoginRequest =
        UserLoginRequest.builder().socialToken("Asd823daz").pushToken("Wesdf0898dfa").build();
    final UserLoginResponse userLoginResponse =
        UserLoginResponse.builder().coupleToken("7E0U7W").socialToken("Asd823daz").build();

//    given(userFindService.loginUser(userLoginRequest)).willReturn(userLoginResponse);

    when(userFindService.loginUser(userLoginRequest)).thenReturn(userLoginResponse);
    // when

    this.mockMvc
        .perform(
            post("/login")
                .content(this.objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "user-login",
                requestFields(
                    fieldWithPath("socialToken").description("소셜 토큰"),
                    fieldWithPath("pushToken").description("푸시 토큰").optional()),
                responseFields(
                    fieldWithPath("socialToken").description("소셜 토큰"),
                    fieldWithPath("coupleToken").description("커플 토큰"))));
  }
}

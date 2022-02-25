package so.ego.re_darling.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.user.application.UserRegisterService;
import so.ego.re_darling.domains.user.application.UserUpdateService;
import so.ego.re_darling.domains.user.application.dto.UserBirthdayUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserMessageUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserNickNameUpdateRequest;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class UserUpdateControllerTest {
  private MockMvc mockMvc;

  @MockBean private UserUpdateService userUpdateService;
  @MockBean private UserRegisterService userRegisterService;

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
  void connectUser() throws Exception {
    final UserConnectRequest userConnectRequest =
        UserConnectRequest.builder().coupleCode("AEJEONG").socialToken("abc").build();

    when(userRegisterService.addUser(any())).thenReturn(1L);
    when(userRegisterService.addUser(any())).thenReturn(2L);

    when(userUpdateService.connectUser(userConnectRequest)).thenReturn(ResponseEntity.ok().build());

    mockMvc
        .perform(
            put("/couple/connect")
                .content("{\"coupleCode\":\"AEJEONG\",\"socialToken\":\"abc\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "user-connect",
                requestFields(
                    fieldWithPath("coupleCode").description("커플코드"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }

  @Test
  void updateBirthday() throws Exception {
    final UserBirthdayUpdateRequest userBirthdayUpdateRequest =
        UserBirthdayUpdateRequest.builder()
            .birthDay(LocalDateTime.now())
            .socialToken("abc")
            .build();
    this.mockMvc
        .perform(
            put("/user/birthday")
                .content(this.objectMapper.writeValueAsString(userBirthdayUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "user-update-birthday",
                requestFields(
                    fieldWithPath("birthDay").description("생일날짜"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }

  @Test
  void updateNickname() throws Exception {
    final UserNickNameUpdateRequest userNickNameUpdateRequest =
        UserNickNameUpdateRequest.builder().nickname("애정").socialToken("abc").build();
    this.mockMvc
        .perform(
            put("/user/nickname")
                .content(this.objectMapper.writeValueAsString(userNickNameUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "user-update-nickName",
                requestFields(
                    fieldWithPath("nickname").description("애칭"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }

  @Test
  void updateStatusMessage() throws Exception {
    final UserMessageUpdateRequest userMessageUpdateRequest =
        UserMessageUpdateRequest.builder().say("행복하자!").socialToken("abc").build();
    this.mockMvc
        .perform(
            put("/user/nickname")
                .content(this.objectMapper.writeValueAsString(userMessageUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "user-update-message",
                requestFields(
                    fieldWithPath("say").description("상태 메시지"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }

  @Test
  void deleteInfo() throws Exception {
    this.mockMvc
        .perform(delete("/user/{socialToken}", "abc").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "user-delete", pathParameters(parameterWithName("socialToken").description("소셜 토큰"))));
  }
}

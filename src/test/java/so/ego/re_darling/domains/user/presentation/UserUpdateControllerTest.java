package so.ego.re_darling.domains.user.presentation;

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
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserRegisterResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class UserUpdateControllerTest {
  private MockMvc mockMvc;

  @MockBean private UserUpdateService userUpdateService;
  @MockBean private UserRegisterService userRegisterService;

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
  void connectUser() throws Exception{
    final UserConnectRequest userConnectRequest = UserConnectRequest.builder().coupleCode("AEJEONG").socialToken("abc").build();
    final UserRegisterResponse user = UserRegisterResponse.builder().id(1L).build();
    final UserRegisterResponse partner = UserRegisterResponse.builder().id(2L).build();


    when(userRegisterService.addUser(any())).thenReturn(user);
    when(userRegisterService.addUser(any())).thenReturn(partner);

    when(userUpdateService.connectUser(userConnectRequest)).thenReturn(ResponseEntity.ok().build());

    mockMvc.perform(put("/couple/connect").content("{\"coupleCode\":\"AEJEONG\",\"socialToken\":\"abc\"}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(
            document("user-connect",requestFields(fieldWithPath("coupleCode").description("커플코드"),fieldWithPath("socialToken").description("소셜 토큰")))
    );
  }

}

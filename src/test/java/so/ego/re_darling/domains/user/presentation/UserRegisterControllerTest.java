package so.ego.re_darling.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.user.application.UserRegisterService;
import so.ego.re_darling.domains.user.application.dto.UserRegisterRequest;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(UserRegisterController.class)
class UserRegisterControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  @MockBean private UserRegisterService userRegisterService;


  @Test
  void create() throws Exception {
    final UserRegisterRequest userRegisterRequest =
        UserRegisterRequest.builder().socialToken("abc").build();


    given(userRegisterService.addUser("abc")).willReturn(1L);

    this.mockMvc
        .perform(
            post("/user")
                .content(this.objectMapper.writeValueAsString(userRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(
            document(
                "user-create", requestFields(fieldWithPath("socialToken").description("소셜토큰"))));
  }
}

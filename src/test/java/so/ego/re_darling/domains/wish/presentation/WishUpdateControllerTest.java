package so.ego.re_darling.domains.wish.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.application.dto.WishListRequest;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishRepository;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class WishUpdateControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired private WishRepository wishRepository;

  @Autowired protected ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    userRepository.save(User.builder().socialToken("abc").build());
    wishRepository.save(Wish.builder().content("바다가기").status(WishStatus.INCOMPLETE).build());
  }

  @Test
  void updateComplete() throws Exception {

    this.mockMvc
        .perform(
            put("/wishlist/{socialToken}/{wishListId}", "abc", "1")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "wish-status-complete",
                pathParameters(
                    parameterWithName("socialToken").description("소셜 토큰"),
                    parameterWithName("wishListId").description("변경할 위시 Index"))));
  }

  @Test
  void deleteStatus() throws Exception {
    this.mockMvc
        .perform(put("/wishlist/delete/{wishListId}", "1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "wish-status-delete",
                pathParameters(parameterWithName("wishListId").description("삭제할 위시 Index"))));
  }

  @Test
  void updateContent() throws Exception {

    final WishListRequest wishListRequest =
        WishListRequest.builder()
            .wishId(1L)
            .content("소래산 가기")
            .wishStatus(WishStatus.COMPLETE)
            .socialToken("abc")
            .build();

    this.mockMvc
        .perform(
            put("/wish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(wishListRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "wish-content-update",
                requestFields(
                    fieldWithPath("wishId").description("위시 Index"),
                    fieldWithPath("content").description("변경할 위시 내용"),
                    fieldWithPath("wishStatus").description("변경할 위시 상태 (삭제예정)"),
                    fieldWithPath("socialToken").description("소셜 토큰"))));
  }
}

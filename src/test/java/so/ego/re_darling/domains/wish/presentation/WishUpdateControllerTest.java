package so.ego.re_darling.domains.wish.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.wish.application.WishUpdateService;
import so.ego.re_darling.domains.wish.application.dto.WishListRequest;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
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
@WebMvcTest(WishUpdateController.class)
class WishUpdateControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @MockBean private WishUpdateService wishUpdateService;

  @Test
  void updateComplete() throws Exception {

    // given
    given(wishUpdateService.stateUpdate("abc", 1L)).willReturn(1L);

    // when
    Wish wish = Wish.builder().build();
    wish.updateStatus(LocalDateTime.now(), WishStatus.COMPLETE);

    this.mockMvc
        .perform(
            put("/wishlist/{socialToken}/{wishListId}", "abc", 1L)
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
    // given
    given(wishUpdateService.delWish(1L)).willReturn(1L);

    this.mockMvc
        .perform(put("/wishlist/delete/{wishListId}", 1L).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "wish-status-delete",
                pathParameters(parameterWithName("wishListId").description("삭제할 위시 Index"))));
  }

  @Test
  void updateContent() throws Exception {

    // given
    final WishListRequest wishListRequest =
        WishListRequest.builder()
            .wishId(1L)
            .content("소래산 가기")
            .wishStatus(WishStatus.COMPLETE)
            .socialToken("abc")
            .build();

    given(wishUpdateService.modifyContent(wishListRequest)).willReturn(1L);

    // when
    Wish wish = Wish.builder().build();
    wish.updateContent("문학산 가기");

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

package so.ego.re_darling.domains.wish.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.wish.application.WishFindService;
import so.ego.re_darling.domains.wish.application.dto.WishListFindAllResponse;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class WishFindControllerTest {
  private MockMvc mockMvc;

  @MockBean private WishFindService wishFindService;

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
  void findAllWish() throws Exception {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy??? MM??? dd??? E??????", Locale.KOREAN);

    final List<WishListFindAllResponse> wishListFindAllResponse =
        Lists.newArrayList(
            WishListFindAllResponse.builder()
                .wishListId(1L)
                .complete_date(null)
                .register_date(
                    simpleDateFormat.format(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .content("?????? ??????")
                .wishUserNickname("??????")
                .status(WishStatus.INCOMPLETE)
                .build());

    when(wishFindService.findAllWish(any())).thenReturn(wishListFindAllResponse);

    this.mockMvc
        .perform(get("/wishlists/{coupleToken}", "AAAAA").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "wish-find-all",
                pathParameters(parameterWithName("coupleToken").description("?????? ??????")),
                responseFields(
                    fieldWithPath("[].wishListId").description("?????? ?????? ID"),
                    fieldWithPath("[].content").description("?????? ??????"),
                    fieldWithPath("[].register_date").description("?????? ?????? ??????"),
                    fieldWithPath("[].complete_date").description("?????? ?????? ??????"),
                    fieldWithPath("[].wishUserNickname").description("?????? ????????? ????????? ?????????"),
                    fieldWithPath("[].status").description("?????? ??????"))));
  }
}

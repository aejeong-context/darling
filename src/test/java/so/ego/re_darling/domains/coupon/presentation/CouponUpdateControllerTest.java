package so.ego.re_darling.domains.coupon.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.coupon.applicateion.CouponUpdateService;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(CouponUpdateController.class)
class CouponUpdateControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private CouponUpdateService couponUpdateService;

  @Test
  void 쿠폰_사용() throws Exception {

    // given
    given(couponUpdateService.useCoupon(1L, "AAAAA")).willReturn(CouponStatus.USED);

    this.mockMvc
        .perform(put("/coupon/{socialToken}/{couponId}", "AAAAA", 1L))
        .andExpect(status().isOk())
        .andDo(
            document(
                "coupon-status-use",
                pathParameters(
                    parameterWithName("socialToken").description("소셜 토큰"),
                    parameterWithName("couponId").description("쿠폰 Index"))));
  }


}

package so.ego.re_darling.domains.coupon.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.coupon.applicateion.CouponRegisterService;
import so.ego.re_darling.domains.coupon.applicateion.dto.CouponResisterRequest;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.UserRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class CouponRegisterControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;
  @Autowired private CoupleRepository coupleRepository;

  @Autowired protected ObjectMapper objectMapper;

  @MockBean
  private CouponRegisterService couponRegisterService;

  @Test
  void addCoupon() throws Exception {

//    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
//    userRepository.save(User.builder().socialToken("abc").couple(couple).build());
//    userRepository.save(User.builder().socialToken("def").couple(couple).build());

    final CouponResisterRequest couponResisterRequest =
        CouponResisterRequest.builder()
            .socialToken("abc")
            .state(CouponStatus.UNUSED)
            .title("커피사주기")
            .content("특별히 스타벅스 사줌")
            .build();

    given(couponRegisterService.addCoupon(couponResisterRequest)).willReturn(1L);

    this.mockMvc
        .perform(
            post("/coupon")
                .content(this.objectMapper.writeValueAsString(couponResisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
            .andDo(print())
        .andDo(
            document(
                "coupon-add",
                requestFields(
                    fieldWithPath("socialToken").description("소셜 토큰"),
                    fieldWithPath("state").description("쿠폰 상태 (삭제예정)"),
                    fieldWithPath("title").description("쿠폰 이름"),
                    fieldWithPath("content").description("쿠폰 내용"))));
  }
}

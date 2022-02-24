package so.ego.re_darling.domains.coupon.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.coupon.domain.Coupon;
import so.ego.re_darling.domains.coupon.domain.CouponRepository;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class CouponFindControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  @Autowired private CoupleRepository coupleRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private CouponRepository couponRepository;

  @BeforeEach
  void setUp() {
    Couple couple = coupleRepository.save(Couple.builder().coupleToken("AAAAA").build());
    User user = userRepository.save(User.builder().socialToken("abc").couple(couple).build());
    User partner = userRepository.save(User.builder().socialToken("def").couple(couple).build());

    couponRepository.save(
        Coupon.builder()
            .status(CouponStatus.UNUSED)
            .sender(user)
            .receiver(partner)
            .title("화 풀어주기")
            .content("맛있는거 사달라는 뜻이야")
            .build());
    couponRepository.save(
            Coupon.builder()
                    .status(CouponStatus.USED)
                    .useDate(LocalDateTime.of(2022,1,25,10,30))
                    .sender(user)
                    .receiver(partner)
                    .title("화 풀어주기")
                    .content("맛있는거 사달라는 뜻이야")
                    .build());
  }

  @Test
  void 사용안한_쿠폰_조회() throws Exception {

    this.mockMvc
        .perform(get("/coupons/{socialToken}", "abc"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "coupon-find-unUsed",
                pathParameters(parameterWithName("socialToken").description("소셜 토큰")),
                responseFields(
                    fieldWithPath("[].userId").description("유저 Index"),
                    fieldWithPath("[].couponId").description("쿠폰 Index"),
                    fieldWithPath("[].title").description("쿠폰 이름"),
                    fieldWithPath("[].content").description("쿠폰 내용"),
                    fieldWithPath("[].sender_id").description("보낸사람 Index"),
                    fieldWithPath("[].receiver_id").description("받는사람 Index"),
                    fieldWithPath("[].issueDate").description("쿠폰 발급 날짜"),
                    fieldWithPath("[].useDate").description("사용한 날짜").optional(),
                    fieldWithPath("[].issueDateCount").description("쿠폰 발급 날짜 경과 일"),
                    fieldWithPath("[].status").description("쿠폰 상태"))));
  }

  @Test
  void 사용한쿠폰_조회()throws Exception{
    this.mockMvc
            .perform(get("/coupons/historys/{socialToken}", "abc"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(
                    document(
                            "coupon-find-used",
                            pathParameters(parameterWithName("socialToken").description("소셜 토큰")),
                            responseFields(
                                    fieldWithPath("[].userId").description("유저 Index"),
                                    fieldWithPath("[].couponId").description("쿠폰 Index"),
                                    fieldWithPath("[].title").description("쿠폰 이름"),
                                    fieldWithPath("[].content").description("쿠폰 내용"),
                                    fieldWithPath("[].sender_id").description("보낸사람 Index"),
                                    fieldWithPath("[].receiver_id").description("받는사람 Index"),
                                    fieldWithPath("[].issueDate").description("쿠폰 발급 날짜"),
                                    fieldWithPath("[].useDate").description("사용한 날짜"),
                                    fieldWithPath("[].issueDateCount").description("쿠폰 발급 날짜 경과 일"),
                                    fieldWithPath("[].status").description("쿠폰 상태"))));
  }

  @Test
  void 사용한쿠폰_3개_조회()throws Exception {
    this.mockMvc
            .perform(get("/coupons/history/{socialToken}", "abc"))
            .andExpect(status().isOk())
            .andDo(
                    document(
                            "coupon-find-limitUsed",
                            pathParameters(parameterWithName("socialToken").description("소셜 토큰")),
                            responseFields(
                                    fieldWithPath("[].userId").description("유저 Index"),
                                    fieldWithPath("[].couponId").description("쿠폰 Index"),
                                    fieldWithPath("[].title").description("쿠폰 이름"),
                                    fieldWithPath("[].content").description("쿠폰 내용"),
                                    fieldWithPath("[].sender_id").description("보낸사람 Index"),
                                    fieldWithPath("[].receiver_id").description("받는사람 Index"),
                                    fieldWithPath("[].issueDate").description("쿠폰 발급 날짜"),
                                    fieldWithPath("[].useDate").description("사용한 날짜"),
                                    fieldWithPath("[].issueDateCount").description("쿠폰 발급 날짜 경과 일"),
                                    fieldWithPath("[].status").description("쿠폰 상태"))));
  }
}

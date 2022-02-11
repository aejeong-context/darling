package so.ego.re_darling.domains.coupon.applicateion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.coupon.applicateion.dto.CouponResisterRequest;
import so.ego.re_darling.domains.coupon.domain.Coupon;
import so.ego.re_darling.domains.coupon.domain.CouponRepository;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CouponRegisterService {

  private final UserRepository userRepository;
  private final CouponRepository couponRepository;

  public Long addCoupon(CouponResisterRequest couponResisterRequest) {
    User user =
        userRepository
            .findBySocialToken(couponResisterRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User SocialToken"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

    Coupon coupon =
        couponRepository.save(
            Coupon.builder()
                .receiver(partner)
                .title(couponResisterRequest.getTitle())
                .content(couponResisterRequest.getContent())
                .sender(user)
                .status(CouponStatus.UNUSED)
                .build());

    return coupon.getId();
  }
}

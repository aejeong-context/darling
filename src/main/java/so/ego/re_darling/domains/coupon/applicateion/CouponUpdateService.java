package so.ego.re_darling.domains.coupon.applicateion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.coupon.domain.Coupon;
import so.ego.re_darling.domains.coupon.domain.CouponRepository;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CouponUpdateService {
  private final CouponRepository couponRepository;

  @Transactional
  public CouponStatus useCoupon(Long couponId, String socialToken) {
    Coupon coupon =
        couponRepository
            .findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid CouponIndex"));
    coupon.updateStatus(LocalDateTime.now(), CouponStatus.USED);

    return coupon.getStatus();
  }
}

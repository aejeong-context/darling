package so.ego.re_darling.domains.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.coupon.applicateion.CouponUpdateService;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;

@RequiredArgsConstructor
@RestController
public class CouponUpdateController {

  private final CouponUpdateService couponUpdateService;

  @PutMapping("/coupon/{socialToken}/{couponId}")
  public ResponseEntity<String> updateCouponStatus(
      @PathVariable String socialToken, @PathVariable Long couponId) {
    CouponStatus couponStatus = couponUpdateService.useCoupon(couponId, socialToken);

    return new ResponseEntity<>(couponId + " has been changed to " + couponStatus, HttpStatus.OK);
  }
}

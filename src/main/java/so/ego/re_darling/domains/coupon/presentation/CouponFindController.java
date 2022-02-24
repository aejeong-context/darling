package so.ego.re_darling.domains.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.coupon.applicateion.CouponFindService;
import so.ego.re_darling.domains.coupon.applicateion.dto.CouponFindAllResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponFindController {
    private final CouponFindService couponFindService;

    @GetMapping("/coupons/{socialToken}")
    public List<CouponFindAllResponse> findAllCoupon(@PathVariable String socialToken) {
        return couponFindService.findAllCoupon(socialToken);
    }

    @GetMapping("/coupons/historys/{socialToken}")
    public List<CouponFindAllResponse> findAllHistoryCoupon(@PathVariable String socialToken) {
        return couponFindService.findAllHistoryCoupon(socialToken);
    }

    @GetMapping("/coupons/history/{socialToken}")
    public List<CouponFindAllResponse> findPreviewCoupon(@PathVariable String socialToken) {
        return couponFindService.findPreviewCoupon(socialToken);
    }
}

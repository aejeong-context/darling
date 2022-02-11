package so.ego.re_darling.domains.coupon.applicateion.dto;

import lombok.Builder;
import lombok.Getter;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;

@Builder
@Getter
public class CouponResisterRequest {
    private String socialToken;
    private String title;
    private String content;
    private CouponStatus state;
}

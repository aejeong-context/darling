package so.ego.re_darling.domains.coupon.applicateion.dto;

import lombok.Builder;
import lombok.Getter;
import so.ego.re_darling.domains.coupon.domain.CouponStatus;


@Builder
@Getter
public class CouponFindAllResponse {
    private Long userId;
    private Long couponId;
    private String title;
    private String content;
    private Long sender_id;
    private Long receiver_id;
    private String issueDate;
    private String useDate;
    private String issueDateCount;
    private CouponStatus status;
}

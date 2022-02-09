package so.ego.re_darling.domains.wish.application.dto;

import lombok.Builder;
import lombok.Getter;
import so.ego.re_darling.domains.wish.domain.WishStatus;

@Builder
@Getter
public class WishListFindAllResponse {
    private Long wishListId;
    private String content;
    private String register_date;
    private String complete_date;
    private String wishUserNickname;
    private WishStatus status;
}

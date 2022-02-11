package so.ego.re_darling.domains.wish.application.dto;

import lombok.Builder;
import lombok.Getter;
import so.ego.re_darling.domains.wish.domain.WishStatus;

@Builder
@Getter
public class WishListRequest {
    private String socialToken;
    private Long wishId;
    private String content;
    private WishStatus wishStatus;
}

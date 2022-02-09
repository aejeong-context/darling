package so.ego.re_darling.domains.wish.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WishResisterRequest {
  private String socialToken;
  private String content;
}

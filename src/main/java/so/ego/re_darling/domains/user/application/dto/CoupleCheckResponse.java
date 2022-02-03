package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoupleCheckResponse {
  private boolean result;
}

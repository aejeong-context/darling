package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DdayDto {
  String name;
  String date;
  int dayCount;
}

package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CoupleDdayResponse {
    private List<DdayDto> DdayList;
}

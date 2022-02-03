package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CoupleFirstDayRequest {
    private String coupleToken;
    private LocalDateTime specialDay;
}

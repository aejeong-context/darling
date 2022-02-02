package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserBirthdayUpdateRequest {
    private String socialToken;
    private LocalDateTime birthDay;

}

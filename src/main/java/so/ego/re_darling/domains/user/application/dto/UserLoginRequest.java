package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {
    private String socialToken;
    private String pushToken;
}

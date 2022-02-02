package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserNickNameUpdateRequest {
    private String socialToken;
    private String nickname;
}

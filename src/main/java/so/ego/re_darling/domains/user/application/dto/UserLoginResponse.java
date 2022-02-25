package so.ego.re_darling.domains.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserLoginResponse {
    private String socialToken;
    private String coupleToken;
}

package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.user.application.dto.UserRegisterRequest;
import so.ego.re_darling.domains.user.application.dto.UserRegisterResponse;
import so.ego.re_darling.domains.user.domain.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserRegisterService {

  private final UserRepository userRepository;

  public UserRegisterResponse addUser(UserRegisterRequest userRegisterRequest) {
    User user =
        userRepository.save(
            User.builder()
                .socialToken(userRegisterRequest.getSocialToken())
                .nickname("미설정")
                .birthday(LocalDateTime.now())
                .statusMessage("")
                .build());
    return UserRegisterResponse.builder().id(user.getId()).build();
  }
}

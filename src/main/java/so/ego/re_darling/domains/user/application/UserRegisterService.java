package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserRegisterService {

  private final UserRepository userRepository;

  public Long addUser(String socialToken) {
    User user =
        userRepository.save(
            User.builder()
                .socialToken(socialToken)
                .nickname("미설정")
                .birthday(LocalDateTime.now())
                .statusMessage(" ")
                .build());
    return user.getId();
  }
}

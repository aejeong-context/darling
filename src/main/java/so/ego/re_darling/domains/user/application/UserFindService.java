package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.user.application.dto.UserFindResponse;
import so.ego.re_darling.domains.user.application.dto.UserLoginRequest;
import so.ego.re_darling.domains.user.application.dto.UserLoginResponse;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class UserFindService {

  private final UserRepository userRepository;
  private final CoupleResisterService coupleResisterService;

  public UserFindResponse findUser(String socialToken) {
    int userFindCount = userRepository.findByTokenCount(socialToken);
    return UserFindResponse.builder().result(userFindCount != 0).build();
  }

  @Transactional
  public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
    User user =
        userRepository
            .findBySocialToken(userLoginRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    if (user.getCouple() == null) {
      Couple couple = coupleResisterService.addCouple();
      user.updateCouple(couple);
    }
    user.updatePushToken(userLoginRequest.getPushToken());
    return UserLoginResponse.builder()
        .socialToken(user.getSocialToken())
        .coupleToken(user.getCouple().getCoupleToken())
        .build();
  }
}

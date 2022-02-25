package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.user.application.dto.UserFindResponse;
import so.ego.re_darling.domains.user.application.dto.UserLoginRequest;
import so.ego.re_darling.domains.user.application.dto.UserLoginResponse;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserFindService {

  private final CoupleRepository coupleRepository;
  private final UserRepository userRepository;
  //  private final CoupleResisterService coupleResisterService;

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
      Couple couple = addCouple();
      user.updateCouple(couple);
    }
    user.updatePushToken(userLoginRequest.getPushToken());
    return UserLoginResponse.builder()
        .socialToken(user.getSocialToken())
        .coupleToken(user.getCouple().getCoupleToken())
        .build();
  }

  @Transactional
  public Couple addCouple() {
    String coupleToken = createToken();
    int coupleCount = coupleRepository.countByCoupleToken(coupleToken);
    if (coupleCount == 0) {
      return coupleRepository.save(
          Couple.builder().coupleToken(coupleToken).firstDay(LocalDateTime.now()).build());
    } else {
      return addCouple();
    }
  }

  public String createToken() {
    String token = "";

    for (int i = 0; i < 6; i++) {
      int r = (int) ((Math.random() * 2) + 1); // 숫자로할지 영어로 할지 랜덤
      char d = (char) ((Math.random() * 10) + 48); // '1' , '2' , '3'~ '9'
      char c = (char) ((Math.random() * 26) + 65); // 대문자
      if (r != 1) {
        token = token + d;
      } else {
        token = token + c;
      }
    }
    return token;
  }
}

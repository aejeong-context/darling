package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CoupleResisterService {

  private final CoupleRepository coupleRepository;

  public Couple addCouple() {
    String coupleToken = createToken();
    int coupleCount = coupleRepository.findByTokenCheck(coupleToken);
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

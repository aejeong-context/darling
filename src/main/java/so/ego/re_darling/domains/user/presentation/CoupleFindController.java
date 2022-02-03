package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.CoupleFindService;
import so.ego.re_darling.domains.user.application.dto.CoupleCheckResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleDdayResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleFindResponse;

@RequiredArgsConstructor
@RestController
public class CoupleFindController {

  private final CoupleFindService coupleFindService;

  // TODO: 2022/02/01 로직에서 coupleToken 필요 없지만
  //  이미 만들어져 있는 API 수정 곤란해서 추 후 프론트와 상의 후 변경 예정
  @GetMapping("/couple/main/{coupleToken}/{socialToken}")
  public CoupleFindResponse findCouple(
      @PathVariable String coupleToken, @PathVariable String socialToken) {
    return coupleFindService.findCouple(coupleToken, socialToken);
  }

  @GetMapping("/couple/check/{socialToken}")
  public CoupleCheckResponse coupleConnectCheck(@PathVariable String socialToken) {
    return coupleFindService.coupleCheck(socialToken);
  }
}

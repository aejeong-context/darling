package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.CoupleUpdateService;
import so.ego.re_darling.domains.user.application.dto.CoupleFirstDayRequest;

@RequiredArgsConstructor
@RestController
public class CoupleUpdateController {

  private final CoupleUpdateService coupleUpdateService;

  @PutMapping("/couple")
  public ResponseEntity updateFirstDay(@RequestBody CoupleFirstDayRequest coupleFirstDayRequest) {
    return coupleUpdateService.updateFirstDay(coupleFirstDayRequest);
  }
}

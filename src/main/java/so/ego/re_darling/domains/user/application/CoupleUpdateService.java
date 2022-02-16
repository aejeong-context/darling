package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.application.dto.CoupleFirstDayRequest;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CoupleUpdateService {
  private final CoupleRepository coupleRepository;

  @Transactional
  public ResponseEntity updateFirstDay(CoupleFirstDayRequest coupleFirstDayRequest) {

    Couple couple =
        coupleRepository
            .findByCoupleToken(coupleFirstDayRequest.getCoupleToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid CoupleToken"));
    ;
    couple.updateFirstDay(coupleFirstDayRequest.getSpecialDay());

    return ResponseEntity.ok().build();
  }
}

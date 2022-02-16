package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterResponse;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class DiaryRegisterService {

  private final CoupleRepository coupleRepository;
  private final DiaryRepository diaryRepository;
  private final UserRepository userRepository;

  public DiaryRegisterResponse addDiary(DiaryRegisterRequest diaryRegisterRequest) {

    Couple couple =
        coupleRepository
            .findByCoupleToken(diaryRegisterRequest.getCoupleToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid CoupleToken"));
    Diary diary =
        diaryRepository.save(
            Diary.builder().couple(couple).date(diaryRegisterRequest.getDate()).build());

    User user =
        userRepository
            .findBySocialToken(diaryRegisterRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));

    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Partner"));

    // Push

    return DiaryRegisterResponse.builder().diaryId(diary.getId()).build();
  }
}

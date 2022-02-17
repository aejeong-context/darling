package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterResponse;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryPlace;
import so.ego.re_darling.domains.diary.domain.DiaryPlaceRepository;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryRegisterService {

  private final CoupleRepository coupleRepository;
  private final DiaryRepository diaryRepository;
  private final DiaryPlaceRepository diaryPlaceRepository;
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

  public void addDiaryPlaces(List<DiaryPlaceRegisterRequest> diaryPlaceList) {
    for (int i = 0; i < diaryPlaceList.size(); i++) {
      Diary diary =
          diaryRepository
              .findById(diaryPlaceList.get(i).getDiaryId())
              .orElseThrow(() -> new IllegalArgumentException("invalid DiaryId Index"));
      diaryPlaceRepository.save(
          DiaryPlace.builder()
              .diary(diary)
              .title(diaryPlaceList.get(i).getName())
              .comment(diaryPlaceList.get(i).getComment())
              .build());
    }
  }
}

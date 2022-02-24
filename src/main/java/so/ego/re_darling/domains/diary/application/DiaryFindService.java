package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.diary.application.dto.DiaryFindAllResponse;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceFindAllResponse;
import so.ego.re_darling.domains.diary.domain.*;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryFindService {

  private final CoupleRepository coupleRepository;
  private final DiaryRepository diaryRepository;
  private final DiaryPlaceRepository diaryPlaceRepository;
  private final DiaryCommentRepository diaryCommentRepository;
  private final UserRepository userRepository;

  public List<DiaryFindAllResponse> findAllDiary(String socialToken, String coupleToken) {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);

    List<DiaryFindAllResponse> diaryFindAllResponseList = new ArrayList<>();

    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));
    User partner =
        userRepository
            .findByPartner(coupleToken, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid partner"));

    Couple couple =
        coupleRepository
            .findByCoupleToken(coupleToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));

    List<Diary> diaryList = diaryRepository.findByCoupleId(couple.getId());

    LocalDate firstDay = LocalDate.from(couple.getFirstDay());

    for (Diary diary : diaryList) {

      List<DiaryPlace> diaryPlaceList = diaryPlaceRepository.findByDiaryId(diary.getId());

      diaryFindAllResponseList.add(
          DiaryFindAllResponse.builder()
              .diaryId(diary.getId())
              .date(simpleDateFormat.format(java.sql.Timestamp.valueOf(diary.getDate())))
              .datingDate(
                  ChronoUnit.DAYS.between(firstDay, LocalDate.from(diary.getDate())) + 1 + "일")
              .DiaryPlaceList(findPlaceList(diaryPlaceList))
              .diaryPlaceNames(
                  diaryPlaceList.stream()
                      .map(DiaryPlace::getTitle)
                      .collect(Collectors.joining(" #", "#", "")))
              .mySay(getDiaryComment(diary.getId(), user.getId()).getComment())
              .yourSay(getDiaryComment(diary.getId(), partner.getId()).getComment())
              .build());
    }
    return diaryFindAllResponseList;
  }

  public List<DiaryPlaceFindAllResponse> findAllDiaryPlace(Long diaryId) {
    List<DiaryPlace> diaryPlaceList = diaryPlaceRepository.findByDiaryId(diaryId);
    return findPlaceList(diaryPlaceList);
  }

  public List<DiaryPlaceFindAllResponse> findPlaceList(List<DiaryPlace> diaryPlaceList) {
    List<DiaryPlaceFindAllResponse> diaryPlaceFindAllResponseList = new ArrayList<>();
    for (DiaryPlace place : diaryPlaceList) {
      diaryPlaceFindAllResponseList.add(
          DiaryPlaceFindAllResponse.builder()
              .diaryPlaceId(place.getId())
              .name(place.getTitle())
              .comment(place.getComment())
              .build());
    }
    return diaryPlaceFindAllResponseList;
  }

  public DiaryComment getDiaryComment(Long diaryIndex, Long userIndex) {
    return diaryCommentRepository
        .findByDiaryIdAndUserId(diaryIndex, userIndex)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Diary or user Index"));
  }
}

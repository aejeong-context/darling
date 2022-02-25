package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryPlace;
import so.ego.re_darling.domains.diary.domain.DiaryPlaceRepository;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;

@RequiredArgsConstructor
@Service
public class DiaryDeleteService {
  private final DiaryRepository diaryRepository;
  private final DiaryPlaceRepository diaryPlaceRepository;

  public void delDiary(Long diaryId) {
    Diary diary =
        diaryRepository
            .findById(diaryId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid DiaryId Index"));
    diaryRepository.delete(diary);
  }

  public Long delDiaryPlace(Long diaryPlaceId) {
    DiaryPlace diaryPlace =
        diaryPlaceRepository
            .findById(diaryPlaceId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid DiaryPlace Index"));
    diaryPlaceRepository.delete(diaryPlace);
    return diaryPlace.getId();
  }
}

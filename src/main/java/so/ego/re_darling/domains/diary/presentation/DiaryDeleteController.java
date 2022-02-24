package so.ego.re_darling.domains.diary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.diary.application.DiaryDeleteService;

@RequiredArgsConstructor
@RestController
public class DiaryDeleteController {

  private final DiaryDeleteService diaryDeleteService;

  @DeleteMapping("/diary/{diaryId}")
  public ResponseEntity<String> delDiary(@PathVariable Long diaryId) {
    diaryDeleteService.delDiary(diaryId);
    return new ResponseEntity<>("Diary Index is  [ " + diaryId + " ] deleted", HttpStatus.OK);
  }

  @DeleteMapping("/diary/place/{diaryPlaceId}")
  public ResponseEntity<String> delDiaryPlace(@PathVariable Long diaryPlaceId) {
    diaryDeleteService.delDiaryPlace(diaryPlaceId);
    return new ResponseEntity<>(
        "Diary Place Index is [ " + diaryPlaceId + " ] deleted", HttpStatus.OK);
  }
}

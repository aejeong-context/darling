package so.ego.re_darling.domains.diary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.diary.application.DiaryUpdateService;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceUpdateRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryUpdateRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class DiaryUpdateController {
  private final DiaryUpdateService diaryUpdateService;

  @PutMapping("/diary")
  public ResponseEntity<String> updateDiaryComment(
      @RequestBody DiaryUpdateRequest diaryUpdateRequest) {
    Map<Long, String> updateDiaryIndex = diaryUpdateService.updateDiaryComment(diaryUpdateRequest);
    return new ResponseEntity<>("Index [ " + updateDiaryIndex + " ] is updated", HttpStatus.OK);
  }

  @PutMapping("/diary/place")
  public ResponseEntity<String> updateDiaryPlace(
      @RequestBody DiaryPlaceUpdateRequest diaryPlaceUpdateRequest) {
    Long updateIndex = diaryUpdateService.updateDiaryPlace(diaryPlaceUpdateRequest);

    return new ResponseEntity<>("Index [ "+updateIndex + " ] is Updated", HttpStatus.OK);
  }
}

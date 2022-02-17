package so.ego.re_darling.domains.diary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.diary.application.DiaryRegisterService;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterResponse;
import so.ego.re_darling.domains.diary.domain.DiaryPlace;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryRegisterController {

  private final DiaryRegisterService diaryRegisterService;

  @PostMapping("/diary")
  public DiaryRegisterResponse addDiary(@RequestBody DiaryRegisterRequest diaryRegisterRequest) {
    return diaryRegisterService.addDiary(diaryRegisterRequest);
  }

  @PostMapping("/diary/places")
  public ResponseEntity<String> addDiaryPlaces(
      @RequestBody List<DiaryPlaceRegisterRequest> diaryPlaceList) {
    diaryRegisterService.addDiaryPlaces(diaryPlaceList);
    return new ResponseEntity<>("A place has been added to the diary.", HttpStatus.CREATED);
  }
}

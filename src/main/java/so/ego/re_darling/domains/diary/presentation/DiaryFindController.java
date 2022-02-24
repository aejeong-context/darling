package so.ego.re_darling.domains.diary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.diary.application.DiaryFindService;
import so.ego.re_darling.domains.diary.application.dto.DiaryFindAllResponse;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceFindAllResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryFindController {

  private final DiaryFindService diaryFindService;

  @GetMapping("/diarys/{socialToken}/{coupleToken}")
  public List<DiaryFindAllResponse> findAllDiary(
      @PathVariable String socialToken, @PathVariable String coupleToken) {
    return diaryFindService.findAllDiary(socialToken, coupleToken);
  }

  @GetMapping("/diary/place/{diaryId}")
  public List<DiaryPlaceFindAllResponse> findAllDiaryPlace(@PathVariable Long diaryId) {
    return diaryFindService.findAllDiaryPlace(diaryId);
  }
}

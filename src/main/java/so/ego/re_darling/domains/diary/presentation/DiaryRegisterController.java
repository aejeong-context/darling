package so.ego.re_darling.domains.diary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.diary.application.DiaryRegisterService;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterResponse;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class DiaryRegisterController {

    private final DiaryRegisterService diaryRegisterService;

    @PostMapping("/diary")
    public DiaryRegisterResponse addDiary(@RequestBody DiaryRegisterRequest diaryRegisterRequest) {
        return diaryRegisterService.addDiary(diaryRegisterRequest);
    }
}

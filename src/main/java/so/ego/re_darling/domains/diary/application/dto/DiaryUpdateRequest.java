package so.ego.re_darling.domains.diary.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiaryUpdateRequest {
    private String socialToken;
    private Long diaryId;
    private String say;
}

package so.ego.re_darling.domains.diary.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiaryPlaceRegisterRequest {
    private Long diaryId;
    private String name;
    private String comment;
}

package so.ego.re_darling.domains.diary.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiaryPlaceFindAllResponse {
    private Long diaryPlaceId;
    private String name;
    private String comment;
}

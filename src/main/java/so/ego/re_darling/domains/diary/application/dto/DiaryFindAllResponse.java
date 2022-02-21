package so.ego.re_darling.domains.diary.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DiaryFindAllResponse {
    private Long diaryId;
    private String datingDate;
    private String date;
    private String diaryPlaceNames;
    private List<DiaryPlaceFindAllResponse> DiaryPlaceList;
    private String mySay;
    private String yourSay;
}

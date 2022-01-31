package so.ego.re_darling.domains.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoupleFindResponse {
    private String coupleToken;
    private String specialDay;
    private String nickname1;

    private String nickname2;

    private int meetingDate;
    private String background_path;
    private String user1_profile_path;
    private String user2_profile_path;

    private String sayMe;
    private String sayYou;
}

package so.ego.re_darling.domains.diary.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DiaryRegisterRequest {
    private String socialToken;
    private String coupleToken;
    private LocalDateTime date;
    private String say;
}

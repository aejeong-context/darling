package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.application.dto.CoupleFindResponse;
import so.ego.re_darling.domains.user.domain.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class CoupleFindService {
  private final UserRepository userRepository;
  private final BackgroundRepository backgroundRepository;
  private final ProfileRepository profileRepository;

  public CoupleFindResponse findCouple(String coupleToken, String socialToken) {
    User user = userRepository.findByToken(socialToken);
    User partner = userRepository.findByPartner(user.getCouple().getCoupleToken(), user.getId());

    Background background = backgroundRepository.findByCoupleId(user.getCouple().getId());
    Profile profile1 = profileRepository.findByUserId(user.getId());
    Profile profile2 = profileRepository.findByUserId(partner.getId());

    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN);
    LocalDate special_date = LocalDate.from(user.getCouple().getFirstDay());
    int countSpecialDay = (int) ChronoUnit.DAYS.between(special_date, LocalDateTime.now()) + 1;

    return CoupleFindResponse.builder()
        .sayMe(user.getStatusMessage())
        .sayYou(partner.getStatusMessage())
        .coupleToken(user.getCouple().getCoupleToken())
        .specialDay(simpleDateFormat1.format(java.sql.Date.valueOf(special_date)))
        .nickname1(user.getNickname())
        .nickname2(partner.getNickname())
        .meetingDate(countSpecialDay)
        .background_path(background.getPath())
        .user1_profile_path(profile1.getPath())
        .user2_profile_path(profile2.getPath())
        .build();
  }
}

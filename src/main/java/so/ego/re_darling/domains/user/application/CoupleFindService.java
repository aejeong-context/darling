package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.application.dto.CoupleCheckResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleDdayResponse;
import so.ego.re_darling.domains.user.application.dto.CoupleFindResponse;
import so.ego.re_darling.domains.user.application.dto.DdayDto;
import so.ego.re_darling.domains.user.domain.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class CoupleFindService {
  private final UserRepository userRepository;
  private final BackgroundRepository backgroundRepository;
  private final ProfileRepository profileRepository;
  private final CoupleRepository coupleRepository;

  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN);

  public CoupleFindResponse findCouple(String coupleToken, String socialToken) {
    User user = userRepository.findBySocialToken(socialToken);
    User partner = userRepository.findByPartner(user.getCouple().getCoupleToken(), user.getId());

    Background background = backgroundRepository.findByCoupleId(user.getCouple().getId());
    Profile profile1 = profileRepository.findByUserId(user.getId());
    Profile profile2 = profileRepository.findByUserId(partner.getId());

    LocalDate special_date = LocalDate.from(user.getCouple().getFirstDay());
    int countSpecialDay = (int) ChronoUnit.DAYS.between(special_date, LocalDateTime.now()) + 1;

    return CoupleFindResponse.builder()
        .sayMe(user.getStatusMessage())
        .sayYou(partner.getStatusMessage())
        .coupleToken(user.getCouple().getCoupleToken())
        .specialDay(simpleDateFormat.format(java.sql.Date.valueOf(special_date)))
        .nickname1(user.getNickname())
        .nickname2(partner.getNickname())
        .meetingDate(countSpecialDay)
        .background_path(background.getPath())
        .user1_profile_path(profile1.getPath())
        .user2_profile_path(profile2.getPath())
        .build();
  }

  public CoupleCheckResponse coupleCheck(String socialToken) {
    User user = userRepository.findBySocialToken(socialToken);
    int coupleUserCount = userRepository.countByCoupleId(user.getCouple().getId());
    return CoupleCheckResponse.builder().result((coupleUserCount == 1) ? false : true).build();
  }

  public CoupleDdayResponse coupleDay(String coupleToken) {
    List<DdayDto> days = new ArrayList<>();
    Couple couple = coupleRepository.findByCoupleToken(coupleToken);
    List<User> userList = userRepository.findByCoupleId(couple.getId());

    User user = userList.get(0);
    User partner = userList.get(1);

    LocalDate userBirthday = getBirthday(LocalDate.from(user.getBirthday()));
    days.add(
        DdayDto.builder()
            .name(user.getNickname() + "의 생일")
            .date(simpleDateFormat.format(userBirthday))
            .dayCount((int) ChronoUnit.DAYS.between(LocalDate.now(), userBirthday))
            .build());
    LocalDate partnerBirthday = getBirthday(LocalDate.from(partner.getBirthday()));
    days.add(
        DdayDto.builder()
            .name(partner.getNickname() + "의 생일")
            .date(simpleDateFormat.format(partnerBirthday))
            .dayCount((int) ChronoUnit.DAYS.between(LocalDate.now(), partnerBirthday))
            .build());

    LocalDate coupleFirstDay = LocalDate.from(couple.getFirstDay());
    int countSpecialDay = (int) ChronoUnit.DAYS.between(coupleFirstDay, LocalDate.now());
    for (int i = 1; i < 4; i++) {
      days.add(
          DdayDto.builder()
              .name((countSpecialDay / 100) + i + "00일")
              .date(
                  simpleDateFormat.format(
                      java.sql.Date.valueOf(
                          coupleFirstDay.plusDays(((countSpecialDay / 100) + i) * 100 - 1))))
              .dayCount((i * 100) - ((countSpecialDay % 100)))
              .build());
    }

    DdayDto meetYear = getMeetYear(couple.getFirstDay());
    days.add(meetYear);

    return CoupleDdayResponse.builder().DdayList(days).build();
  }

  public LocalDate getBirthday(LocalDate birthday) {
    LocalDate nowBirthday = birthday.plusYears(ChronoUnit.YEARS.between(birthday, LocalDate.now()));
    if (nowBirthday.isBefore(LocalDate.now())) {
      return nowBirthday.plusYears(1);
    }
    return nowBirthday;
  }

  public DdayDto getMeetYear(LocalDateTime firstDay) {
    LocalDate meetDay = LocalDate.from(firstDay);
    long countDay = ChronoUnit.YEARS.between(meetDay, LocalDate.now());
    LocalDate specialDay = meetDay.plusYears(countDay);

    specialDay = specialDay.plusYears(1);
    countDay += 1;

    return DdayDto.builder()
        .name(countDay + "주년")
        .date(simpleDateFormat.format(specialDay))
        .dayCount((int) countDay)
        .build();
  }
}

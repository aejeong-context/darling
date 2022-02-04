package so.ego.re_darling.domains.user.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import so.ego.re_darling.domains.user.application.dto.DdayDto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CoupleDdayTest {
  LocalDate localDateNow = LocalDate.from(LocalDateTime.of(2022, 2, 4, 10, 33));
  LocalDate meetDay = LocalDate.from(LocalDateTime.of(1994, 6, 5, 10, 33));
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN);

  @Test
  void getSpecialDay() {
    //
    long countDay = ChronoUnit.YEARS.between(meetDay, localDateNow);
    //
    LocalDate specialDay = meetDay.plusYears(countDay);
    specialDay = specialDay.plusYears(1);
    countDay += 1;
    //
    assertEquals(specialDay.getYear(), localDateNow.getYear());
    assertEquals(countDay, 28);
  }

  @Test
  void getAnn() {

    int countSpecialDay = (int) ChronoUnit.DAYS.between(meetDay, localDateNow);
    String name;
    String date;
    int count;

    for (int i = 1; i < 4; i++) {

      name = (countSpecialDay / 100) + i + "00일";
      date =
          simpleDateFormat.format(
              java.sql.Date.valueOf(meetDay.plusDays(((countSpecialDay / 100) + i) * 100 - 1)));
      count = (i * 100) - ((countSpecialDay % 100));

      System.out.println(name);
      System.out.println(date);
      System.out.println(count);

    }
  }
}

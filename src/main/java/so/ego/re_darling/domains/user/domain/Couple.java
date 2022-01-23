package so.ego.re_darling.domains.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "couple")
@Entity
public class Couple extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String coupleToken;
  private LocalDateTime firstDay;


  @Builder
  public Couple(String coupleToken, LocalDateTime firstDay) {
    this.coupleToken = coupleToken;
    this.firstDay = firstDay;
  }
}

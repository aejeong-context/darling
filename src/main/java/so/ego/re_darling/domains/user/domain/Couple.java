package so.ego.re_darling.domains.user.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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

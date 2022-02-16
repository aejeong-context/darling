package so.ego.re_darling.domains.diary.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;
import so.ego.re_darling.domains.user.domain.Couple;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
@Entity
public class Diary extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime date;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "couple_id")
  private Couple couple;

  @Builder
  public Diary(LocalDateTime date, Couple couple) {
    this.date = date;
    this.couple = couple;
  }
}

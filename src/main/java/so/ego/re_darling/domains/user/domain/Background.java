package so.ego.re_darling.domains.user.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "background")
@Entity
public class Background extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String path;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "couple_id")
  private Couple couple;

  @Builder
  public Background(Couple couple) {
    this.couple = couple;
  }
}

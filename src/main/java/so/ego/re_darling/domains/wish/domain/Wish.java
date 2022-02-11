package so.ego.re_darling.domains.wish.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wish")
@Entity
public class Wish extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;
  private LocalDateTime completeDate;

  @Enumerated(value = EnumType.STRING)
  private WishStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "couple_id")
  private Couple couple;

  @Builder
  public Wish(String content, WishStatus status, User user, Couple couple) {
    this.content = content;
    this.status = status;
    this.user = user;
    this.couple = couple;
  }

  public void updateStatus(LocalDateTime complete_date, WishStatus status) {
    this.completeDate = complete_date;
    this.status = status;
  }

  public void updateStatus(WishStatus wishStatus) {
    this.status = wishStatus;
  }

  public void updateContent(String content) {
    this.content = content;
  }
}

package so.ego.re_darling.domains.wish.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;
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

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Wish(String content, WishStatus status, User user) {
    this.content = content;
    this.status = status;
    this.user = user;
  }
}

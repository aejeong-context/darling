package so.ego.re_darling.domains.user.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String socialToken;
  private String nickname;
  private String pushToken;
  private String statusMessage;
  private LocalDateTime birthday;

  @ManyToOne
  @JoinColumn(name = "couple_id")
  private Couple couple;

  @Builder
  public User(
      @NonNull String socialToken,
      String nickname,
      String pushToken,
      String statusMessage,
      LocalDateTime birthday,
      Couple couple) {
    this.socialToken = socialToken;
    this.nickname = nickname;
    this.pushToken = pushToken;
    this.statusMessage = statusMessage;
    this.birthday = birthday;
    this.couple = couple;
  }
}

package so.ego.re_darling.domains.coupon.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;
import so.ego.re_darling.domains.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
@Entity
public class Coupon extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String title;
  private String content;
  private LocalDateTime useDate;

  @Enumerated(value = EnumType.STRING)
  private CouponStatus status;

  //  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private User receiver;

  //  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @Builder
  public Coupon(
      @NonNull String title,
      String content,
      LocalDateTime useDate,
      CouponStatus status,
      User receiver,
      User sender) {
    this.title = title;
    this.content = content;
    this.useDate = useDate;
    this.status = status;
    this.receiver = receiver;
    this.sender = sender;
  }


}

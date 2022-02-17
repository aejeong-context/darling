package so.ego.re_darling.domains.diary.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;
import so.ego.re_darling.domains.user.domain.User;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary_comment")
@Entity
public class DiaryComment extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String comment;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "diary_id")
  private Diary diary;

  @Builder
  public DiaryComment(String comment, User user, Diary diary) {
    this.comment = comment;
    this.user = user;
    this.diary = diary;
  }

  public void updateComment(String comment) {
    this.comment = comment;
  }
}

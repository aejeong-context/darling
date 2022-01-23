package so.ego.re_darling.domains.diary;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary_palce")
@Entity
public class DiaryPlace extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public DiaryPlace(String title, String comment, Diary diary) {
        this.title = title;
        this.comment = comment;
        this.diary = diary;
    }
}

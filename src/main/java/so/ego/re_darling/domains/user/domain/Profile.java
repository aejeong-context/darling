package so.ego.re_darling.domains.user.domain;

import lombok.*;
import so.ego.re_darling.domains.BaseTimeEntity;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profile")
@Entity
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    @Builder
    public Profile(String path) {
        this.path = path;
    }
}

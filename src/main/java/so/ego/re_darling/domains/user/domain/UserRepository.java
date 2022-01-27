package so.ego.re_darling.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u) from User u where u.socialToken = :socialToken")
    int findByTokenCount(String socialToken);

    @Query("select u from User u where u.socialToken = :socialToken")
    User findByToken(String socialToken);

    @Query("select u from User u where u.couple.coupleToken = :coupleToken and u.id <> :userId")
    User findByPartner(String coupleToken, Long userId);
}

package so.ego.re_darling.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select count(u) from User u where u.socialToken = :socialToken")
  int findByTokenCount(String socialToken);

  Optional<User> findBySocialToken(String socialToken);

  @Query("select u from User u where u.couple.coupleToken = :coupleToken and u.id <> :userId")
  Optional<User> findByPartner(String coupleToken, Long userId);

  int countByCoupleId(Long coupleId);

  List<User> findByCoupleId(Long coupleId);
}

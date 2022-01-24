package so.ego.re_darling.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoupleRepository extends JpaRepository<Couple,Long> {

    @Query("select count(c) from Couple c where c.coupleToken = :coupleToken")
    int findByTokenCheck(String coupleToken);
}

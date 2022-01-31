package so.ego.re_darling.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BackgroundRepository extends JpaRepository<Background,Long> {
    Background findByCoupleId(Long coupleId);
}

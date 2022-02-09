package so.ego.re_darling.domains.wish.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByCoupleIdOrderByStatusDesc(Long coupleId);
}

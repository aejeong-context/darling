package so.ego.re_darling.domains.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT * FROM coupon WHERE user_id = :userId", nativeQuery = true)
    List<Coupon> findByCouponList(Long userId);

    List<Coupon>findByReceiver(Long userId);
}

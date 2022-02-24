package so.ego.re_darling.domains.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

  List<Coupon> findByReceiver(Long userId);

  @Query(
      "select c from Coupon c where c.receiver.id in (:userId, :partnerId) and c.status = 'UNUSED' order by c.id desc")
  List<Coupon> findByAllUnUsedCoupon(Long userId, Long partnerId);

  @Query(
      "select c from Coupon c where c.receiver.id in (:userId, :partnerId) and c.status <> 'UNUSED' order by c.useDate desc")
  List<Coupon> findByAllUsedCoupon(Long userId, Long partnerId);

  @Query(
      value =
          "SELECT * FROM coupon WHERE receiver_id IN (:userId,:partnerId) AND status != 'UNUSED' ORDER BY use_date DESC LIMIT 3",
      nativeQuery = true)
  List<Coupon> findByUsedPreview(Long userId, Long partnerId);
}

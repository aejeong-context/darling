package so.ego.re_darling.domains.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CouponTest {
  @Autowired CouponRepository couponRepository;
  @Autowired UserRepository userRepository;

  @Test
  @DisplayName("LAZY전략 실험")
  void lazyTest() {
    User a = userRepository.save(User.builder().socialToken("a").build());
    User b = userRepository.save(User.builder().socialToken("b").build());
    couponRepository.save(Coupon.builder().receiver(a).sender(b).title("non-null").build());
    couponRepository.save(Coupon.builder().receiver(b).sender(a).title("non-null").build());
    System.out.println("=====Find ALL=====");
    List<Coupon> couponList = couponRepository.findAll();
    System.out.println("==========");
    for (Coupon c : couponList) {
      System.out.println(c.getReceiver().getSocialToken());
    }
  }
}

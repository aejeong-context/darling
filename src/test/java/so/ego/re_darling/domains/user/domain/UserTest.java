package so.ego.re_darling.domains.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DataJpaTest
@Transactional
class UserTest {

  @Autowired UserRepository userRepository;

  @Test
  void 회원생성(){
    User user =  userRepository.save(
            User.builder()
                    .socialToken("abc")
                    .nickname("미설정")
                    .birthday(LocalDateTime.now())
                    .statusMessage(" ")
                    .build());
    System.out.println(user.getCreatedDate());
  }

}

package so.ego.re_darling.domains.wish.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishRepository;
import so.ego.re_darling.domains.wish.domain.WishStatus;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class WishUpdateService {

  private final WishRepository wishRepository;
  private final UserRepository userRepository;

  @Transactional
  public ResponseEntity<String> stateUpdate(String socialToken, Long wishListId) {

    userRepository
        .findBySocialToken(socialToken)
        .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));

    Wish wish =
        wishRepository
            .findById(wishListId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Wish Index"));

    wish.updateStatus(LocalDateTime.now(), WishStatus.COMPLETE);

    return new ResponseEntity<>("The wish update is complete.", HttpStatus.OK);
  }
}

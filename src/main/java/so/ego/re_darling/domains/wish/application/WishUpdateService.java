package so.ego.re_darling.domains.wish.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.application.dto.WishListRequest;
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
  public void stateUpdate(String socialToken, Long wishListId) {

    userRepository
        .findBySocialToken(socialToken)
        .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));

    Wish wish =
        wishRepository
            .findById(wishListId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Wish Index"));

    wish.updateStatus(LocalDateTime.now(), WishStatus.COMPLETE);


  }

  @Transactional
  public void delWish(Long wishListId) {

    Wish wish =
        wishRepository
            .findById(wishListId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid wish Index"));
    wish.updateStatus(WishStatus.DELETE);

  }

  @Transactional
  public void modifyContent(WishListRequest wishListRequest) {
    Wish wishList =
        wishRepository
            .findById(wishListRequest.getWishId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Wish Index"));

    wishList.updateContent(wishList.getContent());


  }
}

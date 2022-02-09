package so.ego.re_darling.domains.wish.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.application.dto.WishResisterRequest;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishRepository;
import so.ego.re_darling.domains.wish.domain.WishStatus;

@RequiredArgsConstructor
@Service
public class WishRegisterService {

  private final UserRepository userRepository;
  private final WishRepository wishRepository;

  public Long AddWish(WishResisterRequest wishResisterRequest) {

    User user =
        userRepository
            .findBySocialToken(wishResisterRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    Wish wish =
        wishRepository.save(
            Wish.builder()
                .content(wishResisterRequest.getContent())
                .user(user)
                .couple(user.getCouple())
                .status(WishStatus.INCOMPLETE)
                .build());

    return wish.getId();
  }
}

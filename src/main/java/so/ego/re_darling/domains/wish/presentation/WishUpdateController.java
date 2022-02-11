package so.ego.re_darling.domains.wish.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.wish.application.WishUpdateService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class WishUpdateController {

  private final WishUpdateService wishUpdateService;

  @PutMapping("/wishlist/{socialToken}/{wishListId}")
  public ResponseEntity<String> stateUpdate(@PathVariable String socialToken, @PathVariable Long wishListId) {
    return wishUpdateService.stateUpdate(socialToken, wishListId);
  }

  @PutMapping("/wishlist/delete/{wishListId}")
  public ResponseEntity<String> delWish(@PathVariable Long wishListId) {
    return wishUpdateService.delWish(wishListId);
  }

}

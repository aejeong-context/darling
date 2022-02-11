package so.ego.re_darling.domains.wish.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.wish.application.WishUpdateService;
import so.ego.re_darling.domains.wish.application.dto.WishListRequest;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class WishUpdateController {

  private final WishUpdateService wishUpdateService;

  @PutMapping("/wishlist/{socialToken}/{wishListId}")
  public ResponseEntity<String> completeStatus(@PathVariable String socialToken, @PathVariable Long wishListId) {
    return wishUpdateService.stateUpdate(socialToken, wishListId);
  }

  @PutMapping("/wishlist/delete/{wishListId}")
  public ResponseEntity<String> delStatus(@PathVariable Long wishListId) {
    return wishUpdateService.delWish(wishListId);
  }

  @PutMapping("/wish")
  public ResponseEntity<String> modifyContent(@RequestBody WishListRequest wishListRequest) {
    return wishUpdateService.modifyContent(wishListRequest);
  }
}

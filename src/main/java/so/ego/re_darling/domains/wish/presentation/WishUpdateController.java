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
  public ResponseEntity<String> completeStatus(
      @PathVariable String socialToken, @PathVariable Long wishListId) {
    wishUpdateService.stateUpdate(socialToken, wishListId);
    return new ResponseEntity<>("The wish update is complete.", HttpStatus.OK);
  }

  @PutMapping("/wishlist/delete/{wishListId}")
  public ResponseEntity<String> delStatus(@PathVariable Long wishListId) {
    wishUpdateService.delWish(wishListId);
    return new ResponseEntity<>("The wish has been deleted.", HttpStatus.OK);
  }

  @PutMapping("/wish")
  public ResponseEntity<String> modifyContent(@RequestBody WishListRequest wishListRequest) {
    wishUpdateService.modifyContent(wishListRequest);
    return new ResponseEntity<>(
        "The contents of Index [ " + wishListRequest.getWishId() + " ] have been updated.",
        HttpStatus.OK);
  }
}

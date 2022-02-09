package so.ego.re_darling.domains.wish.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.wish.application.WishRegisterService;
import so.ego.re_darling.domains.wish.application.dto.WishResisterRequest;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class WishRegisterController {

  private final WishRegisterService wishRegisterService;

  @PostMapping("/wishlist")
  public ResponseEntity<Long> addWish(@RequestBody WishResisterRequest wishResisterRequest) {
    Long wishId = wishRegisterService.AddWish(wishResisterRequest);
    return ResponseEntity.created(URI.create("/wishlist/" + wishId)).build();
  }
}

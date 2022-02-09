package so.ego.re_darling.domains.wish.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.wish.application.WishFindService;
import so.ego.re_darling.domains.wish.application.dto.WishListFindAllResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WishFindController {

  private final WishFindService wishFindService;

  @GetMapping("/wishlists/{coupleToken}")
  public List<WishListFindAllResponse> findAllWish(@PathVariable String coupleToken) {
    return wishFindService.findAllWish(coupleToken);
  }
}

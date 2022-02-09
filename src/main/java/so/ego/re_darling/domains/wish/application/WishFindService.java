package so.ego.re_darling.domains.wish.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.wish.application.dto.WishListFindAllResponse;
import so.ego.re_darling.domains.wish.domain.Wish;
import so.ego.re_darling.domains.wish.domain.WishRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class WishFindService {

  private final WishRepository wishRepository;
  private final CoupleRepository coupleRepository;

  public List<WishListFindAllResponse> findAllWish(String coupleToken) {

    List<WishListFindAllResponse> wishListFindAllResponseList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREAN);

    Couple couple = coupleRepository.findByCoupleToken(coupleToken);

    List<Wish> wishList = wishRepository.findByCoupleIdOrderByStatusDesc(couple.getId());

    for (Wish wish : wishList) {
      wishListFindAllResponseList.add(
          WishListFindAllResponse.builder()
              .wishListId(wish.getId())
              .content(wish.getContent())
              .register_date(
                  simpleDateFormat.format(java.sql.Timestamp.valueOf(wish.getCreatedDate())))
              .complete_date(
                  wish.getCompleteDate() == null
                      ? null
                      : simpleDateFormat.format(java.sql.Timestamp.valueOf(wish.getCompleteDate())))
              .wishUserNickname(wish.getUser().getNickname())
              .status(wish.getStatus())
              .build());
    }

    return wishListFindAllResponseList;
  }
}

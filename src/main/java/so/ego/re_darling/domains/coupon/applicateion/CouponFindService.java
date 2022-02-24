package so.ego.re_darling.domains.coupon.applicateion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.coupon.applicateion.dto.CouponFindAllResponse;
import so.ego.re_darling.domains.coupon.domain.Coupon;
import so.ego.re_darling.domains.coupon.domain.CouponRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponFindService {

  private final CouponRepository couponRepository;
  private final UserRepository userRepository;

  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

  public List<CouponFindAllResponse> findAllCoupon(String socialToken) {
    List<CouponFindAllResponse> couponFindAllResponseList = new ArrayList<>();

    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Partner"));

    List<Coupon> couponList = couponRepository.findByAllUnUsedCoupon(user.getId(), partner.getId());

    for (Coupon coupon : couponList) {
      couponFindAllResponseList.add(
          CouponFindAllResponse.builder()
              .userId(user.getId())
              .couponId(coupon.getId())
              .title(coupon.getTitle())
              .content(coupon.getContent())
              .sender_id(coupon.getSender().getId())
              .receiver_id(coupon.getReceiver().getId())
              .issueDate(
                  simpleDateFormat.format(java.sql.Timestamp.valueOf(coupon.getCreatedDate())))
              .issueDateCount(getIssueDate(coupon.getCreatedDate()))
              .status(coupon.getStatus())
              .build());
    }

    return couponFindAllResponseList;
  }



  public List<CouponFindAllResponse> findAllHistoryCoupon(String socialToken) {
    List<CouponFindAllResponse> couponFindAllResponseList = new ArrayList<>();

    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Partner"));

    List<Coupon> couponList = couponRepository.findByAllUsedCoupon(user.getId(), partner.getId());
    for (Coupon coupon : couponList) {
      couponFindAllResponseList.add(
          CouponFindAllResponse.builder()
              .userId(user.getId())
              .couponId(coupon.getId())
              .title(coupon.getTitle())
              .content(coupon.getContent())
              .sender_id(coupon.getSender().getId())
              .receiver_id(coupon.getReceiver().getId())
              .useDate(simpleDateFormat.format(java.sql.Timestamp.valueOf(coupon.getUseDate())))
              .issueDate(
                  simpleDateFormat.format(java.sql.Timestamp.valueOf(coupon.getCreatedDate())))
              .issueDateCount(getIssueDate(coupon.getCreatedDate()))
              .status(coupon.getStatus())
              .build());
    }
    return couponFindAllResponseList;
  }

  public List<CouponFindAllResponse> findPreviewCoupon(String socialToken) {
    List<CouponFindAllResponse> couponFindAllResponseList = new ArrayList<>();
    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Partner"));

    List<Coupon> couponList = couponRepository.findByUsedPreview(user.getId(), partner.getId());
    for (Coupon coupon : couponList) {
      couponFindAllResponseList.add(
          CouponFindAllResponse.builder()
              .userId(user.getId())
              .couponId(coupon.getId())
              .title(coupon.getTitle())
              .content(coupon.getContent())
              .sender_id(coupon.getSender().getId())
              .receiver_id(coupon.getReceiver().getId())
              .issueDate(
                  simpleDateFormat.format(java.sql.Timestamp.valueOf(coupon.getCreatedDate())))
              .useDate(simpleDateFormat.format(java.sql.Timestamp.valueOf(coupon.getUseDate())))
              .issueDateCount(getIssueDate(coupon.getCreatedDate()))
              .status(coupon.getStatus())
              .build());
    }
    return couponFindAllResponseList;
  }

  private String getIssueDate(LocalDateTime issueDate) {
    return (int) ChronoUnit.DAYS.between(issueDate, LocalDateTime.now()) == 0
            ? "오늘"
            : (int) ChronoUnit.DAYS.between(issueDate, LocalDateTime.now()) + "일 전";
  }
}

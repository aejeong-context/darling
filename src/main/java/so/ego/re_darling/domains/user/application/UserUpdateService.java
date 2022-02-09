package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.coupon.domain.Coupon;
import so.ego.re_darling.domains.coupon.domain.CouponRepository;
import so.ego.re_darling.domains.diary.Diary;
import so.ego.re_darling.domains.diary.DiaryRepository;
import so.ego.re_darling.domains.user.application.dto.UserBirthdayUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserMessageUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserNickNameUpdateRequest;
import so.ego.re_darling.domains.user.domain.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserUpdateService {
  private final UserRepository userRepository;
  private final CoupleRepository coupleRepository;

  private final ProfileRepository profileRepository;
  private final CouponRepository couponRepository;
  private final DiaryRepository diaryRepository;

  @Transactional
  public ResponseEntity connectUser(UserConnectRequest userConnectRequest) throws IOException {
    String newCode = UUID.randomUUID().toString().replace("-", "");
    User user =
        userRepository
            .findBySocialToken(userConnectRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    Couple couple = coupleRepository.findByCoupleToken(userConnectRequest.getCoupleCode());

    if (couple != null) {
      user.updateCouple(couple);
      couple.updateCoupleToken(newCode);
      updateUserPush(userConnectRequest.getSocialToken());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(500).build();
  }

  public void updateUserPush(String socialToken) throws IOException {
    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Invalid Partner")); //    fcmService.sendMessageTo(partner.getPushToken(),
    // "설정", "커플이 연동되었어요. 연결하기를 눌러주세요!");
  }

  @Transactional
  public ResponseEntity updateBirthday(UserBirthdayUpdateRequest userBirthdayUpdateRequest) {

    User user =
        userRepository
            .findBySocialToken(userBirthdayUpdateRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    user.updateBirthday(userBirthdayUpdateRequest.getBirthDay());

    return ResponseEntity.ok().build();
  }

  @Transactional
  public ResponseEntity updateNickname(UserNickNameUpdateRequest userNickNameUpdateRequest) {
    User user =
        userRepository
            .findBySocialToken(userNickNameUpdateRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    user.updateNickname(userNickNameUpdateRequest.getNickname());
    return ResponseEntity.ok().build();
  }

  @Transactional
  public ResponseEntity updateStatusMessage(UserMessageUpdateRequest userMessageUpdateRequest) {
    User user =
        userRepository
            .findBySocialToken(userMessageUpdateRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    user.updateStatusMessage(userMessageUpdateRequest.getSay());
    return ResponseEntity.ok().build();
  }

  // TODO: 2022/02/09 Test 후 ResponseEntity 반환방식 변경 예정
  @Transactional
  public ResponseEntity deleteUser(String socialToken) {
    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
    User partner =
        userRepository
            .findByPartner(user.getCouple().getCoupleToken(), user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Partner"));

    deleteInfo(user);
    deleteInfo(partner);

    userRepository.delete(partner);
    userRepository.delete(user);

    return ResponseEntity.ok().build();
  }

  public void deleteInfo(User user) {

    Profile profile = profileRepository.findByUserId(user.getId());
    //    List<Push> pushList = pushRepository.findAllByUserId(user.getId());
    List<Diary> diaryList = diaryRepository.findByCoupleId(user.getCouple().getId());
    List<Coupon> couponList = couponRepository.findByReceiver(user.getId());

    for (Diary diary : diaryList) {
      diaryRepository.delete(diary);
    }

    for (Coupon coupon : couponList) {
      couponRepository.delete(coupon);
    }
    //    for (Push push : pushList) {
    //      pushRepository.delete(push);
    //    }
    profileRepository.delete(profile);
  }
}

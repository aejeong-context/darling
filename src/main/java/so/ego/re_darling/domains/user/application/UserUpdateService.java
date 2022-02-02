package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.user.application.dto.UserBirthdayUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserMessageUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserNickNameUpdateRequest;
import so.ego.re_darling.domains.user.domain.*;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserUpdateService {
  private final UserRepository userRepository;
  private final CoupleRepository coupleRepository;

  @Transactional
  public ResponseEntity connectUser(UserConnectRequest userConnectRequest) throws IOException {
    String newCode = UUID.randomUUID().toString().replace("-", "");
    User user = userRepository.findByToken(userConnectRequest.getSocialToken());
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
    User user = userRepository.findByToken(socialToken);
    User partner = userRepository.findByPartner(user.getCouple().getCoupleToken(), user.getId());
    //    fcmService.sendMessageTo(partner.getPushToken(), "설정", "커플이 연동되었어요. 연결하기를 눌러주세요!");
  }

  @Transactional
  public ResponseEntity updateBirthday(UserBirthdayUpdateRequest userBirthdayUpdateRequest) {

    User user = userRepository.findByToken(userBirthdayUpdateRequest.getSocialToken());
    user.updateBirthday(userBirthdayUpdateRequest.getBirthDay());

    return ResponseEntity.ok().build();
  }

  @Transactional
  public ResponseEntity updateNickname(UserNickNameUpdateRequest userNickNameUpdateRequest) {
    User user = userRepository.findByToken(userNickNameUpdateRequest.getSocialToken());
    user.updateNickname(userNickNameUpdateRequest.getNickname());
    return ResponseEntity.ok().build();
  }

  @Transactional
  public ResponseEntity updateStatusMessage(UserMessageUpdateRequest userMessageUpdateRequest) {
    User user = userRepository.findByToken(userMessageUpdateRequest.getSocialToken());
    user.updateStatusMessage(userMessageUpdateRequest.getSay());
    return ResponseEntity.ok().build();
  }
}

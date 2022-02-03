package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.UserUpdateService;
import so.ego.re_darling.domains.user.application.dto.UserBirthdayUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserMessageUpdateRequest;
import so.ego.re_darling.domains.user.application.dto.UserNickNameUpdateRequest;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserUpdateController {

  private final UserUpdateService userUpdateService;

  @PutMapping("/couple/connect")
  public ResponseEntity connectUser(UserConnectRequest userConnectRequest) throws IOException {
    return userUpdateService.connectUser(userConnectRequest);
  }

  @PutMapping("/user/birthday")
  public ResponseEntity updateBirthDay(UserBirthdayUpdateRequest userBirthdayUpdateRequest) {
    return userUpdateService.updateBirthday(userBirthdayUpdateRequest);
  }

  @PutMapping("/user/nickname")
  public ResponseEntity updateNickname(UserNickNameUpdateRequest userNickNameUpdateRequest) {
    return userUpdateService.updateNickname(userNickNameUpdateRequest);
  }

  @PutMapping("/user/say")
  public ResponseEntity updateStatusMessage(UserMessageUpdateRequest userMessageUpdateRequest) {
    return userUpdateService.updateStatusMessage(userMessageUpdateRequest);
  }
  @DeleteMapping("/user/{socialToken}")
  public ResponseEntity deleteUser(@PathVariable String socialToken){
    return userUpdateService.deleteUser(socialToken);
  }
}

package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.UserUpdateService;
import so.ego.re_darling.domains.user.application.dto.UserConnectRequest;
import so.ego.re_darling.domains.user.application.dto.UserConnectResponse;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserUpdateController {

  private final UserUpdateService userUpdateService;

  @PutMapping("/couple/connect")
  public ResponseEntity connectUser(UserConnectRequest userConnectRequest) throws IOException {
    return userUpdateService.connectUser(userConnectRequest);
  }
}

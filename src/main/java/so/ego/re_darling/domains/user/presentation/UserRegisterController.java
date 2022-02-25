package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.UserRegisterService;
import so.ego.re_darling.domains.user.application.dto.UserRegisterRequest;

@RequiredArgsConstructor
@RestController
public class UserRegisterController {

  private final UserRegisterService userRegisterService;

  @PostMapping("/user")
  public ResponseEntity<String> addUser(
      @RequestBody UserRegisterRequest userRegisterRequest) {
    System.out.println(userRegisterRequest.getSocialToken());
    Long userId = userRegisterService.addUser(userRegisterRequest.getSocialToken());
    return new ResponseEntity<>(userId+" is CREATED !",HttpStatus.CREATED);
  }
}

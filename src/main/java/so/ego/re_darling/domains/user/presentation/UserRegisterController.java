package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.user.application.UserRegisterService;
import so.ego.re_darling.domains.user.application.dto.UserRegisterRequest;
import so.ego.re_darling.domains.user.application.dto.UserRegisterResponse;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserRegisterController {

  private final UserRegisterService userRegisterService;

  @PostMapping
  public ResponseEntity<UserRegisterResponse> addUser(
      @RequestBody UserRegisterRequest userRegisterRequest) {

    UserRegisterResponse userRegisterResponse = userRegisterService.addUser(userRegisterRequest);
    return ResponseEntity.created(URI.create("/user/" + userRegisterResponse.getId())).build();
  }
}

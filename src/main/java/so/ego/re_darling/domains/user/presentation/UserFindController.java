package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import so.ego.re_darling.domains.user.application.UserFindService;
import so.ego.re_darling.domains.user.application.dto.UserFindResponse;
import so.ego.re_darling.domains.user.application.dto.UserLoginRequest;
import so.ego.re_darling.domains.user.application.dto.UserLoginResponse;

@RequiredArgsConstructor
@RestController
public class UserFindController {
    private final UserFindService userFindService;

    @GetMapping("/user/check/{socialToken}")
    public UserFindResponse findUser(@PathVariable String socialToken){
        return userFindService.findUser(socialToken);
    }

    @PostMapping("/login")
    public UserLoginResponse loginUser(@RequestBody UserLoginRequest userLoginRequest){
        return userFindService.loginUser(userLoginRequest);
    }


}

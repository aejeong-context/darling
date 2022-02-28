package so.ego.re_darling.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import so.ego.re_darling.domains.user.application.ProfileUpdateService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ProfileUpdateController {
    private final ProfileUpdateService profileUpdateService;

    @PostMapping("/profile/{socialToken}")
    public String updateProfile(@PathVariable String socialToken, @RequestParam(required = false) MultipartFile profileImage) throws IOException {
        return profileUpdateService.updateProfile(socialToken, profileImage);
    }
}

package so.ego.re_darling.domains.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import so.ego.re_darling.commons.aws.S3FileUploaderService;
import so.ego.re_darling.domains.user.domain.Profile;
import so.ego.re_darling.domains.user.domain.ProfileRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class ProfileUpdateService {
  private final ProfileRepository profileRepository;
  private final UserRepository userRepository;
  private final S3FileUploaderService s3FileUploader;

  @Transactional
  public String updateProfile(String socialToken, MultipartFile profileImage) {

    User user =
        userRepository
            .findBySocialToken(socialToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));
    Profile profile = profileRepository.save(Profile.builder().user(user).path(" ").build());

    String filePath = s3FileUploader.uploadFile("profile/" + profile.getId(), profileImage);

    profile.updatePath(filePath);

    return filePath;
  }
}

package so.ego.re_darling.domains.user.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.commons.aws.S3FileUploaderService;
import so.ego.re_darling.domains.user.application.ProfileUpdateService;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(ProfileUpdateService.class)
class ProfileUpdateControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private ProfileUpdateService profileUpdateService;

  @MockBean private S3FileUploaderService s3FileUploaderService;

  @Test
  void 이미지업로드() throws Exception {

    String fileName = "testFile";
    String contentType = "png";
    String filePath = "src/test/resources/image/ae.png";

    MockMultipartFile image = getMockFile(fileName, contentType, filePath);

    String path = s3FileUploaderService.uploadFile("profile/1", image);
    when(s3FileUploaderService.uploadFile("profile/1", image)).thenReturn(path);

    System.out.println(path);
  }

  private MockMultipartFile getMockFile(String fileName, String contentType, String path)
      throws IOException {
    FileInputStream fileInputStream = new FileInputStream(path);
    return new MockMultipartFile(
        fileName, fileName + "." + contentType, contentType, fileInputStream);
  }
}

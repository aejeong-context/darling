package so.ego.re_darling.commons.aws;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileUploader {
    String uploadFile(String uploadName, MultipartFile multipartFile);
    void deleteFile(String path);
}

package so.ego.re_darling.commons.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class S3FileUploaderService implements S3FileUploader {

  private static Logger logger = Logger.getLogger(S3FileUploaderService.class.getName());

  private String BUCKET_NAME = "ego-workspace-fileserver";

  private String BUCKET = "https://ego-workspace-fileserver.s3-ap-northeast-2.amazonaws.com/";

  private final AmazonS3 s3Client;

  @Override
  public String uploadFile(String uploadName, MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(MediaType.IMAGE_PNG_VALUE);
    objectMetadata.setContentLength(file.getSize());
    String uploadPath = createObjectPath(uploadName, file.getOriginalFilename());
    PutObjectRequest putObjectRequest = null;
    try {
      putObjectRequest =
          new PutObjectRequest(BUCKET_NAME, uploadPath, file.getInputStream(), objectMetadata)
              .withCannedAcl(CannedAccessControlList.PublicRead);
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    s3Client.putObject(putObjectRequest);

    logger.info(BUCKET + uploadPath + " were uploaded. ");

    return BUCKET + uploadPath;
  }

  private String createObjectPath(String key, String fileName) {
    return String.join(
        "/", key, LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY/MM-dd")), fileName);
  }

  @Override
  public void deleteFile(String path) {
    String key = path.split(".com/")[1];
    try {
      DeleteObjectsRequest multiObjectDeleteRequest =
          new DeleteObjectsRequest(BUCKET_NAME).withKeys(key).withQuiet(false);

      DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
      int successfulDeletes = delObjRes.getDeletedObjects().size();
      logger.info("Total of " + successfulDeletes + " were deleted. ");
    } catch (AmazonServiceException e) {
      e.printStackTrace();
    } catch (SdkClientException e) {
      e.printStackTrace();
    }
    s3Client.deleteObject(BUCKET_NAME, key);
  }
}

package so.ego.re_darling.commons.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
  @Value("${aws.s3.ACCESS_KEY}")
  private String accessKey;

  @Value("${aws.s3.SECRET_KEY}")
  private String secretKey;

  @Bean
  public AmazonS3 s3() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
  }
}

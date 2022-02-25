package so.ego.re_darling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ReDarlingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReDarlingApplication.class, args);
  }
}

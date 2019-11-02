package kraang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author y.hagel
 */
@ImportResource({"classpath:quartz-config.xml"})
@SpringBootApplication(scanBasePackages = "Hagel")
public class KraangApplication {
  public static void main(String[] args) {
    SpringApplication.run(KraangApplication.class, args);
  }
}
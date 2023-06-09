package pt.pmrelvas.pmrawssqstest.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String region;
    private String accessKeyId;
    private String secretAccessKey;
    private String queueUrl;

}

package pt.pmrelvas.pmrawssqstest.network.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.pmrelvas.pmrawssqstest.domain.properties.AwsProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Component
@Slf4j
public class AmazonSqsClient {

    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;

    @Autowired
    public AmazonSqsClient(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                awsProperties.getAccessKeyId(), awsProperties.getSecretAccessKey());
        sqsClient = SqsClient.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    public void pullEvents() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getQueueUrl())
                .maxNumberOfMessages(5)
                .build();
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);
        ObjectMapper om = new ObjectMapper();
        try {
            log.info("Received event: {}", om.writeValueAsString(receiveMessageResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package pt.pmrelvas.pmrawssqstest.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.pmrelvas.pmrawssqstest.network.aws.AmazonSqsClient;

@Slf4j
@RestController
@RequestMapping("api/pull")
@RequiredArgsConstructor
public class PullController {

    private final AmazonSqsClient amazonSqsClient;

    @PostMapping("/sqs")
    public void pullEvent() {
        log.info("pull SQS event called");
        amazonSqsClient.pullEvents();
    }
}

package demo.consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * A service component for TODO
 *
 * @author sergiu bodiu
 */
@SpringBootApplication
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}

@Service
class ConsumerService {
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "words")
    public void onReceiving(Word message, @Header(KafkaHeaders.OFFSET) Integer offset,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Processing topic = {}, partition = {}, offset = {}, message = {}",
                topic, partition, offset, message);
    }
}

class Word {

    private final String id;
    private final String definition;

    @JsonCreator
    public Word(@JsonProperty("id") String id,
                @JsonProperty("definition") String definition) {
        this.id = id;
        this.definition = definition;
    }

    public String getId() {
        return id;
    }
    
    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}

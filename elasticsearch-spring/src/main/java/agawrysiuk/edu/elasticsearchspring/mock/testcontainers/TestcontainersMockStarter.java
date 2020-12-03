package agawrysiuk.edu.elasticsearchspring.mock.testcontainers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@RequiredArgsConstructor
public class TestcontainersMockStarter {

    private static final Logger logger = LoggerFactory.getLogger(TestcontainersMockStarter.class);

    @Value("${mock.testcontainers.enabled:false}")
    private boolean mock;

    private final ElasticsearchContainer elasticsearchContainer;

    private String dockerImageName = "docker.elastic.co/elasticsearch/elasticsearch";

    @PostConstruct
    public void startIfMockEnv() {
        if(this.mock) {
            logger.info("Starting Elastcsearch mock.");
            elasticsearchContainer.start();
        }
    }

    @PreDestroy
    public void stopMockEnv() {
        if(this.mock && elasticsearchContainer != null) {
            logger.info("Stopping Elasticsearch mock.");
            elasticsearchContainer.stop();
        }
    }
}

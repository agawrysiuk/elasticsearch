package agawrysiuk.edu.elasticsearchspring.mock.testcontainers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@Configuration
public class ContainerMockConfiguration {

    @Bean
    public ElasticsearchContainer elasticsearchContainer() {
        return new ElasticsearchContainer();
    }
}

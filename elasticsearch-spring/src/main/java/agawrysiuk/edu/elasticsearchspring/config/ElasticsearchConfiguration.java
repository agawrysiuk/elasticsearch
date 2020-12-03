package agawrysiuk.edu.elasticsearchspring.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories("agawrysiuk.edu.elasticsearchspring.repository")
//@ComponentScan({ "agawrysiuk.edu.elasticsearchspring.elastic" })
public class ElasticsearchConfiguration {

    @Value("${mock.testcontainers.enabled:false}")
    private boolean mock;

    private final ElasticsearchContainer elasticsearchContainer;

    @Bean
    @DependsOn({"testcontainersMockStarter", "elasticsearchContainer"})
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(mock
                        ? HttpHost.create(elasticsearchContainer.getHttpHostAddress())
                        : new HttpHost("localhost", 9200, "http")));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(restHighLevelClient());
    }
}

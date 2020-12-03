package agawrysiuk.edu.elasticsearchspring.elastic;

import agawrysiuk.edu.elasticsearchspring.elastic.clients.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Configuration
class ElasticClientFactory {

    @Bean
    Map<ClientType, ElasticClient> clients(Collection<ElasticClient> clients) {
        return clients.stream().collect(toMap(ElasticClient::getType, Function.identity()));
    }

    @Bean
    GetClient getClient(RestHighLevelClient restHighLevelClient) {
        return new GetClient(restHighLevelClient);
    }

    @Bean
    SaveClient saveClient(RestHighLevelClient restHighLevelClient) {
        return new SaveClient(restHighLevelClient);
    }

    @Bean
    SearchClient searchClient(RestHighLevelClient restHighLevelClient) {
        return new SearchClient(restHighLevelClient);
    }

    @Bean
    SearchScrollClient searchScrollClient(RestHighLevelClient restHighLevelClient) {
        return new SearchScrollClient(restHighLevelClient);
    }

    @Bean
    AggregationsClient aggregationsClient(RestHighLevelClient restHighLevelClient) {
        return new AggregationsClient(restHighLevelClient);
    }

}

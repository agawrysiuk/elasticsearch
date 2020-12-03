package agawrysiuk.edu.elasticsearchspring.aggregations;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import agawrysiuk.edu.elasticsearchspring.elastic.clients.AggregationsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AggregationsService {

    private final Map<ClientType, ElasticClient> clients;

    public String getAverage(String field) {
        return ((AggregationsClient) clients.get(ClientType.AGGREGATIONS)).getAverage(field);
    }

    public String getTerm(String field) {
        return ((AggregationsClient) clients.get(ClientType.AGGREGATIONS)).getTerm(field);
    }

    public String getMatrix(String... fields) {
        return ((AggregationsClient) clients.get(ClientType.AGGREGATIONS)).getMatrix(fields);
    }
}

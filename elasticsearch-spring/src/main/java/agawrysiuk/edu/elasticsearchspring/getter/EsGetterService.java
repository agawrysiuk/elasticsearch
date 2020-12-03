package agawrysiuk.edu.elasticsearchspring.getter;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import agawrysiuk.edu.elasticsearchspring.elastic.clients.GetClient;
import agawrysiuk.edu.elasticsearchspring.elastic.clients.SearchClient;
import agawrysiuk.edu.elasticsearchspring.elastic.clients.SearchScrollClient;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EsGetterService {

    private final Map<ClientType, ElasticClient> clients;

    public SearchHit[] searchAll() {
        SearchResponse searchResponse = ((SearchClient) clients.get(ClientType.SEARCH)).searchAll();
        return searchResponse.getHits().getHits();
    }

    public String get(String id) {
        GetResponse getResponse = ((GetClient) clients.get(ClientType.GET)).getById(id);
        return getResponse.getSourceAsString();
    }

    public SearchHit[] search(String name) {
        SearchResponse searchResponse = ((SearchClient) clients.get(ClientType.SEARCH)).searchByName(name);
        return searchResponse.getHits().getHits();
    }

    public SearchResponse searchAllScrolled(int size, String scrollId) {
        return scrollId == null
                ? ((SearchScrollClient) clients.get(ClientType.SEARCH_SCROLL)).searchScrolled(size)
                : ((SearchScrollClient) clients.get(ClientType.SEARCH_SCROLL)).searchScrolled(scrollId, size);
    }
}

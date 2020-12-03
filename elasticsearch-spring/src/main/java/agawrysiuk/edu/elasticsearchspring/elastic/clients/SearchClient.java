package agawrysiuk.edu.elasticsearchspring.elastic.clients;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.CANT_DOWNLOAD;
import static agawrysiuk.edu.elasticsearchspring.json.utils.FieldNames.NAME;

public class SearchClient extends ElasticClient {

    public SearchClient(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public ClientType getType() {
        return ClientType.SEARCH;
    }

    public SearchResponse searchAll() {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .size(1000)
                        .sort("name.keyword")
                );
        logger.info("Getting all documents.");
        try {
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }

    public SearchResponse searchByName(String name) {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .source(new SearchSourceBuilder()
                        .query(new MatchQueryBuilder(NAME, name)
                                .fuzziness(Fuzziness.ZERO))
                        .size(1000)
                );
        logger.info("Getting documents containing {} in the name.", name);
        try {
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }
}

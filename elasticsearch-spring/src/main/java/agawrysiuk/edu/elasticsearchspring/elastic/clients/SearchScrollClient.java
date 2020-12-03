package agawrysiuk.edu.elasticsearchspring.elastic.clients;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.CANT_DOWNLOAD;

public class SearchScrollClient extends ElasticClient {

    private final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

    public SearchScrollClient(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public ClientType getType() {
        return ClientType.SEARCH_SCROLL;
    }

    public SearchResponse searchScrolled(int size) {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .scroll(this.scroll)
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .size(size)
                );

        try {
            return searchBatch(searchRequest, size);
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }

    public SearchResponse searchScrolled(String scrollId, int size) {
        try {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            SearchResponse searchResponse = scrollBatch(scrollRequest, size);
            if((searchResponse.getHits().getHits() == null || searchResponse.getHits().getHits().length == 0)) {
                clearScroll(scrollId);
            }
            return searchResponse;
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }

    private SearchResponse searchBatch(SearchRequest searchRequest, int size) throws IOException {
        logger.info("Searching batch size: {} from Elasticsearch", size);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        logger.info("Got response from Elasticsearch, scrollId: {}, number of hits: {}",
                searchResponse.getScrollId(),
                searchResponse.getHits().getHits().length
        );
        return searchResponse;
    }

    private SearchResponse scrollBatch(SearchScrollRequest scrollRequest, int size) throws IOException {
        logger.info("Scrolling batch size: {} from Elasticsearch", size);
        SearchResponse searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
        logger.info("Got response from Elasticsearch, scrollId: {}, number of hits: {}",
                searchResponse.getScrollId(),
                searchResponse.getHits().getHits().length
        );
        return searchResponse;
    }

    private void clearScroll(String scrollId) throws IOException {
        logger.info("End of search, clearing ScrollRequest");
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        logger.info("Ending clearing with status: {}", clearScrollResponse.isSucceeded() ? "OK" : "ERROR");
    }
}

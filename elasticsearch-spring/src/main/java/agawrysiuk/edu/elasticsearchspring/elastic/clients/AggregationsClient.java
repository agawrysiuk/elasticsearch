package agawrysiuk.edu.elasticsearchspring.elastic.clients;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.MatrixStatsAggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.matrix.stats.ParsedMatrixStats;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.CANT_DOWNLOAD;

public class AggregationsClient extends ElasticClient {

    public AggregationsClient(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public ClientType getType() {
        return ClientType.AGGREGATIONS;
    }

    public String getAverage(String field) {
        String avgField = "avg_" + field;
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .size(0)
                        .aggregation(AggregationBuilders
                                .avg(avgField)
                                .field(field)
                        )
                );
        logger.info("Getting average for field: {}", field);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            ParsedAvg parsedAvg = searchResponse.getAggregations().get(avgField);
            return String.valueOf(parsedAvg.getValue());
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }

    public String getTerm(String field) {
        String fieldAgg = "agg_" + field;
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .size(0)
                        .aggregation(AggregationBuilders
                                .terms(fieldAgg)
                                .field(field)
                        )
                );
        logger.info("Getting terms for field: {}", field);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            ParsedLongTerms parsedLongTerms = searchResponse.getAggregations().get(fieldAgg);
            List<Terms.Bucket> buckets = parsedLongTerms.getBuckets().stream()
                    .sorted(Comparator.comparing(MultiBucketsAggregation.Bucket::getKeyAsString))
                    .peek(bucket -> logger.info("Found key: {} with number of values: {}", bucket.getKey(), bucket.getDocCount()))
                    .collect(Collectors.toList());
            return new ObjectMapper().writeValueAsString(buckets);
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }

    public String getMatrix(String... fields) {
        String fieldAgg = "agg_stats";
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME)
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .size(0)
                        .aggregation(MatrixStatsAggregationBuilders
                                .matrixStats(fieldAgg)
                                .fields(Arrays.asList(fields))
                        )
                );
        logger.info("Getting matrix_stats for fields: {}", Arrays.toString(fields));
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            ParsedMatrixStats parsedMatrixStats = searchResponse.getAggregations().get(fieldAgg);
            // work on your stats here
            // ...
            return new ObjectMapper().writeValueAsString(parsedMatrixStats);
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }
}

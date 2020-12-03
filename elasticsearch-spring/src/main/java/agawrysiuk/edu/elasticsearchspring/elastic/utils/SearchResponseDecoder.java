package agawrysiuk.edu.elasticsearchspring.elastic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class SearchResponseDecoder {

    public static String writeResponseAsJson(SearchResponse searchResponse) throws JsonProcessingException {
        return writeResponseAsJson(searchResponse.getHits().getHits());
    }

    public static String writeResponseAsJson(SearchHit[] searchHits) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
                Arrays.stream(searchHits)
                        .map(SearchHit::getSourceAsString)
                        .collect(Collectors.toList())
        );
    }
}

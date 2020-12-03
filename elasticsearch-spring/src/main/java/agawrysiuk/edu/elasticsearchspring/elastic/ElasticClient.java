package agawrysiuk.edu.elasticsearchspring.elastic;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public abstract class ElasticClient {

    protected static final Logger logger = LoggerFactory.getLogger(ElasticClient.class);
    protected final RestHighLevelClient restHighLevelClient;
    public static String INDEX_NAME;

    public abstract ClientType getType();

    @Value("${elasticsearch.index.name:es-edu-index}")
    public void setIndexName(String indexName) {
        INDEX_NAME = indexName;
    }
}

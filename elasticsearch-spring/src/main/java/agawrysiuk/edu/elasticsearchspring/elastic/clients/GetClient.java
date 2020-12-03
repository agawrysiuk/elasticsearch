package agawrysiuk.edu.elasticsearchspring.elastic.clients;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import agawrysiuk.edu.elasticsearchspring.exceptions.DoesntExistException;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.CANT_DOWNLOAD;

public class GetClient extends ElasticClient {

    public GetClient(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public ClientType getType() {
        return ClientType.GET;
    }

    public GetResponse getById(String id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, id);
        logger.info("Getting document with id: {}", id);
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return getResponse;
            }
            throw new DoesntExistException("Document with id: " + id + " doesn't exist.");
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_DOWNLOAD.getText());
        }
    }
}

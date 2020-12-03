package agawrysiuk.edu.elasticsearchspring.elastic.clients;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import agawrysiuk.edu.elasticsearchspring.json.utils.FieldNames;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.CANT_SAVE;
import static agawrysiuk.edu.elasticsearchspring.json.utils.FieldNames.NAME;

public class SaveClient extends ElasticClient {

    public SaveClient(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public ClientType getType() {
        return ClientType.SAVE;
    }

    public void addArrayToElasticSearch(String array) {
        JSONArray jsonArray = new JSONArray(array);
        for (Object object : jsonArray) {
            addObjectToElasticSearch(object.toString());
        }
    }

    public void addObjectToElasticSearch(String object) {
        JSONObject jsonObject = new JSONObject(object);
        IndexRequest request = new IndexRequest()
                .index(INDEX_NAME)
                .id(jsonObject.getString(FieldNames.ID))
                .source(object, XContentType.JSON);

        logger.info("Saving {}", jsonObject.getString(NAME));
        IndexResponse response;
        try {
            response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ElasticsearchException(CANT_SAVE.getText());
        }
        logger.info("Saving {} ended with code {}", jsonObject.getString(NAME), response.getResult());
    }
}

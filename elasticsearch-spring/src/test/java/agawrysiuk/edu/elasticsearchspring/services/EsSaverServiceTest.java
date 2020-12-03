package agawrysiuk.edu.elasticsearchspring.services;

import agawrysiuk.edu.elasticsearchspring.exceptions.EmptyRequestException;
import agawrysiuk.edu.elasticsearchspring.exceptions.InvalidBodyException;
import agawrysiuk.edu.elasticsearchspring.getter.EsGetterService;
import agawrysiuk.edu.elasticsearchspring.saver.EsSaverService;
import org.assertj.core.api.Assertions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static agawrysiuk.edu.elasticsearchspring.utils.FileReader.readFile;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "mock.testcontainers.enabled=true",
        "mock.sampledata.enabled=false",
        "mock.sampledata.resttemplate=false",
        "elasticsearch.index.name=test-index"
})
public class EsSaverServiceTest {

    @Value("${elasticsearch.index.name}")
    private String indexName;
    @Autowired
    private EsSaverService esSaverService;
    @Autowired
    private EsGetterService esGetterService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Before
    public void removeAll() throws IOException {
        if(restHighLevelClient.indices().exists(new GetIndexRequest(indexName),RequestOptions.DEFAULT)) {
            DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
            request.setQuery(QueryBuilders.matchAllQuery());
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        }
    }

    @Test
    public void should_save_an_array_of_three_objects() throws IOException, InterruptedException {
        String object = readFile("/test-array.json", EsSaverServiceTest.class);
        ResponseEntity responseEntity = esSaverService.save(object);

        Assertions.assertThat(responseEntity).isEqualTo(ResponseEntity.ok().build());
        Thread.sleep(1000);
        Assertions.assertThat(esGetterService.searchAll()).hasSize(3);
    }

    @Test
    public void should_save_a_one_object() throws IOException, InterruptedException {
        String object = readFile("/test-object.json", EsSaverServiceTest.class);
        ResponseEntity responseEntity = esSaverService.save(object);

        Assertions.assertThat(responseEntity).isEqualTo(ResponseEntity.ok().build());
        Thread.sleep(1000);
        Assertions.assertThat(Arrays.stream(esGetterService.searchAll())).hasSize(1);
    }

    @Test(expected = EmptyRequestException.class)
    public void should_throw_exception_for_empty_object() {
        esSaverService.save("");
    }

    @Test(expected = InvalidBodyException.class)
    public void should_throw_exception_for_incorrect_object_structure() {
        esSaverService.save("SOME_INCORRECT_OBJECT");
    }

}

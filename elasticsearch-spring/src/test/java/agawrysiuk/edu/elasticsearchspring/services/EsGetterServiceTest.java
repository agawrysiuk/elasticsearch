package agawrysiuk.edu.elasticsearchspring.services;

import agawrysiuk.edu.elasticsearchspring.exceptions.DoesntExistException;
import agawrysiuk.edu.elasticsearchspring.getter.EsGetterService;
import agawrysiuk.edu.elasticsearchspring.saver.EsSaverService;
import org.assertj.core.api.Assertions;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static agawrysiuk.edu.elasticsearchspring.utils.FileReader.readFile;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "mock.testcontainers.enabled=true",
        "mock.sampledata.enabled=false",
        "mock.sampledata.resttemplate=false",
        "elasticsearch.index.name=test-index"
})
public class EsGetterServiceTest {

    @Autowired
    private EsSaverService esSaverService;
    @Autowired
    private EsGetterService esGetterService;
    private boolean setUpIsDone = false;

    @Before
    public void add_objects() throws IOException, InterruptedException {
        if (!this.setUpIsDone) {
            String object = readFile("/test-array.json", EsGetterServiceTest.class);
            esSaverService.save(object);
            this.setUpIsDone = true;
            Thread.sleep(1000);
        }
    }

    @Test
    public void should_return_three_objects() {
        Assertions.assertThat(esGetterService.searchAll()).hasSize(3);
    }

    @Test
    public void should_return_named_object() {
        Assertions.assertThat(esGetterService.search("Unique")).hasSize(1);
    }

    @Test
    public void should_return_id_d_object() {
        Assertions.assertThat(esGetterService.get("1_MAIN")).isNotEmpty();
    }

    @Test(expected = DoesntExistException.class)
    public void should_throw_exception_for_non_existing_document() {
        esGetterService.get("BLAH");
    }

    @Test
    public void should_make_scrolled_search() {
        SearchResponse searchResponse = esGetterService.searchAllScrolled(2, null);
        Assertions.assertThat(searchResponse.getHits().getHits()).hasSize(2);
        String scrollId = searchResponse.getScrollId();

        searchResponse = esGetterService.searchAllScrolled(2, scrollId);
        Assertions.assertThat(searchResponse.getHits().getHits()).hasSize(1);

        searchResponse = esGetterService.searchAllScrolled(2, scrollId);
        Assertions.assertThat(searchResponse.getHits().getHits()).hasSize(0);
    }

}

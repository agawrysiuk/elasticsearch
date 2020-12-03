package agawrysiuk.edu.elasticsearchspring.mock.testcontainers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "mock.testcontainers.enabled=true", "mock.sampledata.enabled=false" })
public class TestcontainersMockRunningTest {

    @Autowired
    private ElasticsearchContainer elasticsearchContainer;

    @Test
    public void containerRunningAfterStart() {
        Assertions.assertTrue(elasticsearchContainer.isRunning());
    }
}
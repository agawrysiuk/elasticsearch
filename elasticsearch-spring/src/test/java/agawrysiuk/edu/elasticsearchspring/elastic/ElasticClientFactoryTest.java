package agawrysiuk.edu.elasticsearchspring.elastic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "mock.testcontainers.enabled=false", "mock.sampledata.enabled=false" })
public class ElasticClientFactoryTest {

    @Autowired
    private Map<ClientType, ElasticClient> clients;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void return_non_zero_clients_after_startup() {
        assertThat(clients).isNotEmpty();
    }

    @Test
    public void check_for_number_of_elasticclient_beans() {
        assertEquals(applicationContext.getBeanNamesForType(ElasticClient.class).length, clients.size());
    }
}
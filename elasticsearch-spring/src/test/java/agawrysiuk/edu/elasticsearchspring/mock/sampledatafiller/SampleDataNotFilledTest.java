package agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller;

import agawrysiuk.edu.elasticsearchspring.repository.SampleDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "mock.testcontainers.enabled=true", "mock.sampledata.enabled=false" })
public class SampleDataNotFilledTest {

    @Autowired
    private SampleDocumentRepository sampleDocumentRepository;

    @Test
    public void noSampleData() {
        assertThat(sampleDocumentRepository.findAll()).hasSize(0);
    }
}
package agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller;

import agawrysiuk.edu.elasticsearchspring.repository.SampleDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "mock.testcontainers.enabled=true",
        "mock.sampledata.enabled=false",
        "mock.sampledata.resttemplate=false"
})
public class SampleDataFillerSampleDocumentRepositoryTest {

    @Autowired
    private SampleDataFiller filler;
    @MockBean
    private SampleDocumentRepository sampleDocumentRepository;


    @Test
    public void repositoryFilledWithRestTemplate() throws URISyntaxException {
        filler.fillEsWithSampleData();
        verify(sampleDocumentRepository, times(1)).saveAll(any());
    }

}
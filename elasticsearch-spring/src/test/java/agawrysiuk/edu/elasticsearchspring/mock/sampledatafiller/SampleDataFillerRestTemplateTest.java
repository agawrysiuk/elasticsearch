package agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "mock.testcontainers.enabled=true",
        "mock.sampledata.enabled=false",
        "mock.sampledata.resttemplate=true"
})
public class SampleDataFillerRestTemplateTest {

    @Autowired
    private SampleDataFiller filler;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${server.port}")
    private String serverPort;

    @Test
    public void repositoryFilledWithRestTemplate() throws URISyntaxException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:" + serverPort + "/save")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));
        filler.fillEsWithSampleData();
        mockServer.verify();
    }

}
package agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller;

import agawrysiuk.edu.elasticsearchspring.feign.FeignControllersClient;
import agawrysiuk.edu.elasticsearchspring.repository.SampleDocumentRepository;
import agawrysiuk.edu.elasticsearchspring.repository.model.SampleDocument;
import agawrysiuk.edu.elasticsearchspring.utils.FileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.UPLOADING_INTERRUPTED;

@Configuration
@RequiredArgsConstructor
public class SampleDataFiller {

    private static final Logger logger = LoggerFactory.getLogger(SampleDataFiller.class);
    private final RestTemplate restTemplate;
    private final SampleDocumentRepository repository;
    private final FeignControllersClient feignControllersClient;

    @Value("${mock.sampledata.path}")
    private String filePath;

    @Value("${server.port}")
    private String serverPort;

    @Value("${mock.sampledata.resttemplate:false}")
    private boolean restTemplateUsed;

    public void fillEsWithSampleData() {
        try {
            String jsonToUpload = FileReader.readFile(filePath);
            if (this.restTemplateUsed) {
                logger.info("Using restTemplate for filling data.");
                feignControllersClient.save(jsonToUpload);
//                restTemplate.postForObject("http://localhost:" + serverPort + "/save", jsonToUpload, ResponseEntity.class);
            } else {
                logger.info("Using SampleDocumentRepository for filling data.");
                List<SampleDocument> sampleDocuments = new ObjectMapper().readValue(jsonToUpload, new TypeReference<List<SampleDocument>>() {
                });
                repository.saveAll(sampleDocuments);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(UPLOADING_INTERRUPTED.getText());
        }
    }

}

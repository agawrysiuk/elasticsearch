package agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SampleDataConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SampleDataConfiguration.class);
    private final SampleDataFiller sampleDataFiller;

    @Value("${mock.sampledata.enabled:false}")
    private boolean enabled;

    public void fillEsWithSampleData() {
        if(this.enabled) {
            logger.info("Filling Elasticsearch with sample data starting.");
            sampleDataFiller.fillEsWithSampleData();
            logger.info("Filling Elasticsearch with sample data finished.");
        }
    }
}

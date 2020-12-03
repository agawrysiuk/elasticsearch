package agawrysiuk.edu.elasticsearchspring.saver;

import agawrysiuk.edu.elasticsearchspring.elastic.ClientType;
import agawrysiuk.edu.elasticsearchspring.elastic.ElasticClient;
import agawrysiuk.edu.elasticsearchspring.elastic.clients.SaveClient;
import agawrysiuk.edu.elasticsearchspring.exceptions.EmptyRequestException;
import agawrysiuk.edu.elasticsearchspring.exceptions.FileLoadingException;
import agawrysiuk.edu.elasticsearchspring.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.EMPTY_REQUEST;
import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.INVALID_BODY;

@Component
@RequiredArgsConstructor
public class EsSaverService {

    private static final Logger logger = LoggerFactory.getLogger(EsSaverService.class);
    private final Map<ClientType, ElasticClient> clients;

    public ResponseEntity save(String json) {
        logger.info("Saving {} to Elasticsearch.", json);
        if(StringUtils.isEmpty(json)) {
            throw new EmptyRequestException(EMPTY_REQUEST.getText());
        }
        if (json.startsWith("{")) {
            ((SaveClient) clients.get(ClientType.SAVE)).addObjectToElasticSearch(json);
        } else if (json.startsWith("[")) {
            ((SaveClient) clients.get(ClientType.SAVE)).addArrayToElasticSearch(json);
        } else {
            throw new InvalidBodyException(INVALID_BODY.getText());
        }
        logger.info("Object saved to Elasticsearch.");
        return ResponseEntity.ok().build();
    }
}

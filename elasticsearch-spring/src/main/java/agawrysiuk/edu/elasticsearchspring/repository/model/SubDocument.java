package agawrysiuk.edu.elasticsearchspring.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "es-edu-index")
@Getter
@Setter
public class SubDocument {
    @Id
    private String id;
    private String name;
    private int numberOfClicks;
}

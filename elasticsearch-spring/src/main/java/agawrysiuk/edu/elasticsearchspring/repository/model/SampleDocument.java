package agawrysiuk.edu.elasticsearchspring.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "es-edu-index")
@Getter
@Setter
public class SampleDocument {

    @Id
    private String id;
    private String name;
    private int numberOfClicks;
    @Field(type = FieldType.Nested, includeInParent = true)
    private SubDocument subDocument;
}

package agawrysiuk.edu.elasticsearchspring.repository;

import agawrysiuk.edu.elasticsearchspring.repository.model.SampleDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface SampleDocumentRepository extends ElasticsearchRepository<SampleDocument, String> {

    List<SampleDocument> findAll();

    Optional<SampleDocument> findById(String s);

    Page<SampleDocument> findAll(Pageable pageable);
}

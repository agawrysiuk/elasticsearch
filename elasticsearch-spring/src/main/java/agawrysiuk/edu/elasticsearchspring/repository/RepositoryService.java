package agawrysiuk.edu.elasticsearchspring.repository;

import agawrysiuk.edu.elasticsearchspring.repository.model.SampleDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryService {

    private final SampleDocumentRepository repository;

    public List<SampleDocument> findAll() {
        return repository.findAll();
    }

    public void save(SampleDocument document) {
        repository.save(document);
    }

    public void saveAll(List<SampleDocument> list) {
        repository.saveAll(list);
    }
}

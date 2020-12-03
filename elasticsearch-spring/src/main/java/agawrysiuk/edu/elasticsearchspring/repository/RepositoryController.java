package agawrysiuk.edu.elasticsearchspring.repository;

import agawrysiuk.edu.elasticsearchspring.repository.model.SampleDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repository")
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryService service;

    @GetMapping("/search")
    public List<SampleDocument> search() {
        return service.findAll();
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody SampleDocument document) {
        service.save(document);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/saveAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody List<SampleDocument> list) {
        service.saveAll(list);
        return ResponseEntity.ok().build();
    }
}

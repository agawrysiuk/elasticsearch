package agawrysiuk.edu.elasticsearchspring.getter;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EsGetterController {

    private final EsGetterService esGetterService;

    @GetMapping("/get/{id}")
    public String getOne(@PathVariable String id) {
        return esGetterService.get(id);
    }

    @GetMapping("/search")
    public SearchHit[] search(@RequestParam(required = false) String name) {
        return name == null
                ? esGetterService.searchAll()
                : esGetterService.search(name);
    }

    @GetMapping("/search-all-scrolled")
    public SearchResponse searchAllScrolled(@RequestParam int size, @RequestParam(required = false) String scrollId) {
        return esGetterService.searchAllScrolled(size, scrollId);
    }
}

package agawrysiuk.edu.elasticsearchspring.saver;

import agawrysiuk.edu.elasticsearchspring.json.utils.FieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EsSaverController {

    private final EsSaverService esSaverService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody String json) {
        return esSaverService.save(json);
    }
}

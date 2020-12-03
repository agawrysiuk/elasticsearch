package agawrysiuk.edu.elasticsearchspring.aggregations;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AggregationsController {

    private final AggregationsService aggregationsService;

    @GetMapping("/agg-avg")
    public String aggAvg(@RequestParam String field) {
        return aggregationsService.getAverage(field);
    }

    @GetMapping("/agg-term")
    public String aggTerm(@RequestParam String field) {
        return aggregationsService.getTerm(field);
    }

    @GetMapping("/agg-matrix")
    public String aggMatrix(@RequestParam String... fields) {
        return aggregationsService.getMatrix(fields);
    }
}

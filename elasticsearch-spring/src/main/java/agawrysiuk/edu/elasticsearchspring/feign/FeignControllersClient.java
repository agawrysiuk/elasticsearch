package agawrysiuk.edu.elasticsearchspring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "feignControllersClient", url = "localhost:${server.port}")
public interface FeignControllersClient {

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    ResponseEntity save(@RequestBody String json);

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    String search();
}

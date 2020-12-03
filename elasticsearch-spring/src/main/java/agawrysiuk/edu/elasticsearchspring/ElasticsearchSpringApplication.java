package agawrysiuk.edu.elasticsearchspring;

import agawrysiuk.edu.elasticsearchspring.mock.sampledatafiller.SampleDataConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class ElasticsearchSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ElasticsearchSpringApplication.class, args);
		context.getBean(SampleDataConfiguration.class).fillEsWithSampleData();
	}

}

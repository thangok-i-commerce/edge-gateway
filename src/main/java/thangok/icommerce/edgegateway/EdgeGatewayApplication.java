package thangok.icommerce.edgegateway;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class EdgeGatewayApplication {

    @Autowired
    RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.forEach(x -> {
            log.info(x.toString());
        });
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*Module")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("Module", "");
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();

            log.info(name);
        });
        return groups;
    }

    public static void main(String[] args) {
        SpringApplication.run(EdgeGatewayApplication.class, args);
    }

}

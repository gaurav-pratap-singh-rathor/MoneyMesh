package gaurav.microservices.moneymesh.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//I created this Configuration , that contains few beans , here the custom routes are defined
@Configuration
public class ApiGatewayConfiguration {

    //Implemented Local filter
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get") // If the path is "http://localhost:8765/get" then it redirected to "http://httpbin.org:80"
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")   //The gateway before calling URI it will add the header as "MyURI" and request parameters.
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**") //If the path contains this then redirect to load balanced[lb] currency-exchange service
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }

}

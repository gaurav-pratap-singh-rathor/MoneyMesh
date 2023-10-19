package gaurav.microservices.moneymesh.apigateway;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,    // Implemented Global filter
                             GatewayFilterChain chain) {
        logger.info("Path of the request received -> {}",   //The path of service will be printed in logger everytime we invoke the gateway URL.
                exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
//This function will apply a global filter (of printing URL in logger) on this gateway service.
}

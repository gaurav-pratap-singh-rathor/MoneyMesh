# MoneyMesh

- Designed and developed a comprehensive microservices project, including Currency Exchange and Currency Conversion Microservices.
- Implemented dynamic scaling capabilities using Spring Boot,  Spring Cloud LoadBalancer , Feign for synchronous data exchange.
- Inter-service communication facilitated exchange rate retrieval.
- Implemented Spring Cloud Config Server for centralized configuration management.
- Netflix Eureka Naming Server for service discovery
- API Gateway for centralized API access and security
- Zipkin Distributed Tracing Server for monitoring and troubleshooting in the microservices project.

 These implementations significantly bolstered scalability, resilience, and maintainability in the microservices project.


<aside>

| Application  | Local Port Number|
| --- | --- |
| Limits Microservice | 8080, 8081, ... |
| Spring Cloud Config Server | 8888 |
|  Currency Exchange Microservice | 8000, 8001, 8002, .. |
| Currency Conversion Microservice | 8100, 8101, 8102, ... |
|  Netflix Eureka Naming Server |  8761 |
| API Gateway | 8765 |
| Zipkin Distributed Tracing Server | 9411 |



</aside>

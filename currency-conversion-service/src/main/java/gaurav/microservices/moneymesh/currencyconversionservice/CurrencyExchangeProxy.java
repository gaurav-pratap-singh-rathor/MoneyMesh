package gaurav.microservices.moneymesh.currencyconversionservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*****************************************************************
 here we have hardcoded the exchange-service url , due to this automatic load balancing will not be occurring here because this service will talk to 8000:Exchange-service every time , not with 8001:8002
 Inorder to solve this load balancing problem we want Conversion-service to talk to exchange-service using a dynamically provided url.
So  we will use the naming-server-service which will  provide the url of any random instance of exchange-service that are registered with naming-server-service.
So whenever conversion service want to talk with exchange service using feign , the naming-server-service will create and provide a different instance of exchange service like 8001:8002:8003
************************************************************************/
//@FeignClient(name="currency-exchange", url="localhost:8000")  [Without Load Balance]

@FeignClient(name="currency-exchange") // currency-exchange is name of service to which we want to connect , name  defined in application.properties

public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(      //Same method that we wrote in exchange controller , but returned type is CurrencyConversion here , feign will automatically map the response with CurrencyConversion Bean.
            @PathVariable String from,
            @PathVariable String to);


}

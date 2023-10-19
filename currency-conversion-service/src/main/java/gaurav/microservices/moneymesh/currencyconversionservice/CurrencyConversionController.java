package gaurav.microservices.moneymesh.currencyconversionservice;


import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from); //(key: from ,value : from)
        uriVariables.put("to",to);

        //The rest template will automatically map the responded (CurrencyExchange) values to CurrencyConversion but the only condition is both pojo class should have same structure.
        //getForEntity will take 3 input and return ResponseEntity
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity    // Inputs => 1. URI input 2. name of type class which has same structure as returned value 3. here  we also pass the values of from and to ,that we used in URI.we pass them as hashmap.
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",     //creating GET request to this url and storing JASON response in responseEntity.
                        CurrencyConversion.class, uriVariables);

        //All values of currencyExchange will get stored in CurrencyConversion object. Both have same fields so values get automatically mapped.

        CurrencyConversion currencyConversion = responseEntity.getBody(); //.getBody will Extract body(Fields) from response entity ,and mapped all fields to fields of currencyConversion Bean.
        //Now the currencyConversion object has all values that are returned from CurrencyExchange.

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),  //getting conversionMultiple of currencyExchange , saved in currencyConversion Variable.
                quantity.multiply(currencyConversion.getConversionMultiple()),  // Calculating totalCalculatedAmount
                currencyConversion.getEnvironment()+ " " + "rest template");    //Environment is 8000 which is a CurrencyExchange environment

    }

    // to use feign we have to create an interface for proxy.
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "feign");

    }


}

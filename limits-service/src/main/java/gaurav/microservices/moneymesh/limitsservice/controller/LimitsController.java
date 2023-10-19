package gaurav.microservices.moneymesh.limitsservice.controller;

import gaurav.microservices.moneymesh.limitsservice.bean.Limits;
import gaurav.microservices.moneymesh.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")     //http://localhost:8080/limits
    public Limits retrieveLimits() {
        return new Limits(configuration.getMinimum(),    //returning Min, max value which we get from Configuration class.
                configuration.getMaximum());            //Configuration class will fetch the values from config microservice instead of application.properties

    }
}

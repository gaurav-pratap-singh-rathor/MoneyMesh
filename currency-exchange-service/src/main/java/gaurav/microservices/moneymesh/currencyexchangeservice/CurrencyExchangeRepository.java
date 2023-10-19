package gaurav.microservices.moneymesh.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepostiory<T,ID>
// JpaRepository accepts two parameter -> Currency exchange which is a entity that has to be managed & long is primary key.
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
        CurrencyExchange findByFromAndTo(String from, String to);
        // the implementation of this abstract method will be automatically provided by Spring Data JPA.
        // JPA will convert this(findByFromTo) to a SQL query
}

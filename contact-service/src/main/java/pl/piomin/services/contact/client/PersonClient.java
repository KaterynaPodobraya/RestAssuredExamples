package pl.piomin.services.contact.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pl.piomin.services.contact.model.Person;

@FeignClient("person-service")
public interface PersonClient {

	@GetMapping("/persons/{id}")
	Person findPersonById(@PathVariable("id") Integer id);
	
}

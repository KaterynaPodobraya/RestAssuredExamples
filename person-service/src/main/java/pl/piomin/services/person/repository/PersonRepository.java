package pl.piomin.services.person.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Repository;

import pl.piomin.services.person.model.Gender;
import pl.piomin.services.person.model.Person;
import pl.piomin.services.person.model.PersonBuilder;

@Repository
public class PersonRepository {

	private List<Person> persons = new ArrayList<>();
	
	public Person add(Person person) {
		person.setId(persons.size()+1);
		persons.add(person);
		return person;
	}
	
	public Person findById(Integer id) {
		Optional<Person> person = persons.stream().filter(a -> a.getId().equals(id)).findFirst();
		if (person.isPresent())
			return person.get();
		else {
			try{
				return fillRepository();
			}
			catch(ParseException e){}
		}
		return null;
	}
	
	public List<Person> findAll() {
		return persons;
	}

	public Person fillRepository() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = sdf.parse("2019-06-14 15:05:36");


		System.out.println(newDate + "Application date !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		Person person = new PersonBuilder()
				.withId(1)
				.withFirstName("Katya")
				.withLastName("Podobra")
				.withBirthDate(newDate)
				.withAccountNo("888800055")
				.withGender(Gender.FEMALE)
				.withPhoneNo("60001265")
				.withCity("Kyiv")
				.withCountry("Ukraine")
				.withHouseNo(100)
				.withStreet("Borshagivska")
				.withEmail("kateryna.podobraya@gmail.com")
				.withPostalCode("03044").build();

		return add(person);
	}
}



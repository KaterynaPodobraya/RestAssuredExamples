package pl.piomin.services.contact;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.contact.client.PersonClient;
import pl.piomin.services.contact.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//@AutoConfigureStubRunner(ids = {"pl.piomin.services:person-service:+:stubs:8090"}, consumerName = "contact-consumer",  stubsPerConsumer = true, stubsMode = StubsMode.REMOTE, repositoryRoot = "http://192.168.99.100:8081/artifactory/libs-snapshot-local")
@AutoConfigureStubRunner(ids = {"pl.piomin.services:person-service:+:stubs:8090"}, consumerName = "contact-consumer",  stubsPerConsumer = true, stubsMode = StubsMode.LOCAL)
@DirtiesContext
public class PersonConsumerContractTestContact {

	@Autowired
	private PersonClient personClient;
	
	@Test
	public void verifyPerson() {
		Person p = personClient.findPersonById(1);
		Assert.assertNotNull(p);
		Assert.assertEquals(1, p.getId().intValue());
	}
	
}

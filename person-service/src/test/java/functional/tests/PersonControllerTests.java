package functional.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.assertj.core.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.Common;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piomin.services.person.PersonApplication;
import pl.piomin.services.person.model.Gender;
import pl.piomin.services.person.repository.PersonRepository;

import javax.validation.constraints.AssertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static pl.piomin.services.person.model.Gender.FEMALE;


@SpringBootTest(classes = {PersonApplication.class})
@RunWith(SpringRunner.class)
public class PersonControllerTests {

    @Autowired
    PersonRepository personRepository;

    @BeforeClass
    public static void setUpRestAssured(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/persons";
        RestAssured.port = 8090;
    }

    @Test
    public void personWithId2IsPresent(){
        Response response =  RestAssured
                .given()
                .when().get("/2")
                .then().body("firstName", equalTo("Katya"))
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void personWithId2IsPresentWithPathParams(){
        Response response =  RestAssured
                .given().pathParam("id", 2)
                .when().get("/{id}")
                .then().body("firstName", equalTo("Katya"))
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void personWithId2IsPresentWithPathParamsSecondApproach(){
        int id = 2;
        Response response =  RestAssured
                .given()
                .when().get("/{id}", id)
                .then().body("firstName", equalTo("Katya"))
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void personControllerWithRequestSpecification(){

        RequestSpecification req_specification = new RequestSpecBuilder().addPathParam("id", 3).build();
        ResponseSpecification res_specification = new ResponseSpecBuilder().expectBody("firstName", equalTo("Katya"))
                .expectStatusCode(200).build();

        RestAssured
                .given().spec(req_specification)
                .when().get("/{id}")
                .then().spec(res_specification);
        }

    @Test
    public void checkPesrsonControllerBody() throws ParseException {
        int id = 1;
        String [] emails = new String[]{"k@mail.ru", "kateryna.podobraya@gmail.com"};

        String dt = "2019-06-14 15:05:36";

        System.out.println(dt);


        Response response =  RestAssured
                .given()
                .when().get("/{id}", id)
                .then().body("firstName", is("Katya"))
                .body("lastName", is("Podobra"))
                //.body("birthDate",  equalTo (new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-13")))
                //.body("birthDate",  equalTo (personRepository.findById(2).getBirthDate()))
                //.body("birthDate",  equalTo ("2019-06-14 15:05:36"))
                .body("birthDate",  equalTo (dt))
                .body("gender", equalTo(Gender.FEMALE.toString()))
                .body("contact.phoneNo", equalTo("60001265"))
                .body("contact.email", isIn(Arrays.asList(emails)))
                .body("accountNumber", is("888800055"))
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void checkPesrsonControllerCookies() throws ParseException {
               Response response =  RestAssured
                .given()
                .when().get("/2")
                .then()
                .extract().response();

               int cookiesSize = response.getDetailedCookies().size();

        System.out.println(cookiesSize);
        Assert.assertEquals(1, cookiesSize);

        System.out.println(response.asString());
    }

    @Test
    public void checkPesrsonControllerHeaders() throws ParseException {
        Response response =  RestAssured
                .given()
                .when().get("/2")
                .then()
                .extract().response();

        int headersSize = response.getHeaders().asList().size();
         response.getHeaders().asList().stream().forEach (header -> System.out.println(header));

        System.out.println(headersSize);
        Assert.assertEquals(3, headersSize);

        System.out.println(response.asString());

        response.getHeader("Transfer-Encoding").matches("chunked");

        LocalDate now = LocalDate.now();
        response.getHeader("Date").contains(String.valueOf(now.getDayOfMonth()));
        response.getHeader("Date").contains(String.valueOf(now.getMonth()));
        response.getHeader("Date").contains(String.valueOf(now.getYear()));
        response.getHeader("Date").contains(String.valueOf(now.getDayOfWeek()));
        response.getHeader("Content-Type").contentEquals("application/json;charset=UTF-8");

    }

    @Test
    public void checkPesrsonControllerSinglePath() throws ParseException {
        Assert.assertTrue(RestAssured.given().when().get("/2").path("address.city").equals("Kyiv"));

    }

    @Test
    public void checkPesrsonControllerStatusLine() throws ParseException {
        String statusLine = RestAssured.given().when().get("/2").statusLine();
        System.out.println( "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + statusLine + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Test
    public void checkPesrsonController() throws ParseException {
        String statusLine = RestAssured. given().when().get("/2").statusLine();
        System.out.println( "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + statusLine + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }



}

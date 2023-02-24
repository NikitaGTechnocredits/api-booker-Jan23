package bookerapi;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteBookingTest extends TestBase {

	@BeforeClass
	public void generateToken() {
		init();
	}
	
	@Test
	public void deleteBookingIdTest() {
		payload.setFirstname("Shashank");
		payload.setLastname("Garad");
		Response resDel = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Cookie", "token="+token)
				.log().all()
				.when()
				.delete("/booking/"+bookingId);
		
		Assert.assertEquals(resDel.getStatusCode(), Status_Code.CREATED);
		
		Response resGet = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(resGet.getStatusCode(), Status_Code.NOT_FOUND);
	}
	
}

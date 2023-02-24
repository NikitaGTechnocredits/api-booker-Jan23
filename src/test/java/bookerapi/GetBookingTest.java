package bookerapi;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.CreateBookingRequest;

public class GetBookingTest extends TestBase {
	
	@BeforeClass
	public void generateToken() {
		init();
	}

	@Test
	public void getAllBookingTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.jsonPath().getList("bookingid").contains(bookingId));
		
	}

	@Test
	public void getBookingIdTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		validateResponse(res, TestBase.payload,"");
		
	}
	
	@Test (priority = 4, enabled = false)
	public void getBookingIdDeserializedTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.as(CreateBookingRequest.class).equals(TestBase.payload));
	}

}

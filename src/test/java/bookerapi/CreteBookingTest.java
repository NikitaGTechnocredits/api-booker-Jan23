package bookerapi;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreteBookingTest extends TestBase {

	@BeforeClass
	public void generateToken() {
		init();
	}

	@Test (priority = 1)
	public void createBookingTestPojo() {
		

		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json").body(payload).when().post("/booking");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		bookingId = res.jsonPath().getInt("bookingid");
		Assert.assertTrue(bookingId > 0);
		
		validateResponse(res, payload,"booking.");
	}
		
	@Test(enabled = false)
	public void createBookingTest() {
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Nikita\",\r\n" + "    \"lastname\" : \"Bhad\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when().post("/booking");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	}
}

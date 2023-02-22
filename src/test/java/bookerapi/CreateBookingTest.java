package bookerapi;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Bookingdates;
import pojo.CreateBookingRequest;

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;

	@BeforeClass
	public void generateToken() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}")
				.when().post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
	}

	@Test (priority = 1)
	public void createBookingTestPojo() {
		Bookingdates bookingDates = new Bookingdates();
		bookingDates.setCheckin("2023-01-01");
		bookingDates.setCheckout("2024-01-01");

		payload = new CreateBookingRequest();
		payload.setFirstname("Nikita");
		payload.setLastname("Bhad");
		payload.setTotalprice(100);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("Breakfast");
		payload.setBookingdates(bookingDates);

		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.headers("Accept", "application/json").body(payload).when().post("/booking");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		bookingId = res.jsonPath().getInt("bookingid");
		Assert.assertTrue(bookingId > 0);
		
		validateResponse(res, payload,"booking.");
	}
	
	@Test (priority = 2, enabled = false)
	public void getAllBookingTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.jsonPath().getList("bookingid").contains(bookingId));
		
	}

	@Test (priority = 3)
	public void getBookingIdTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		validateResponse(res, payload,"");
		
	}
	
	@Test (priority = 4, enabled = false)
	public void getBookingIdDeserializedTest() {
		Response res = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.as(CreateBookingRequest.class).equals(payload));
	}
	
	@Test (priority = 5, enabled = false)
	public void updateBookingIdTest() {
		payload.setFirstname("Kriday");
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.headers("Cookie", "token="+token)
				.log().all()
				.body(payload)
				.when()
				.put("/booking/"+bookingId);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.as(CreateBookingRequest.class).equals(payload));
	}
	
	@Test (priority = 6, enabled = false)
	public void partialUpdateBookingIdTest() {
		payload.setFirstname("Shashank");
		payload.setLastname("Garad");
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.headers("Cookie", "token="+token)
				.log().all()
				.body(payload)
				.when()
				.patch("/booking/"+bookingId);
		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(res.as(CreateBookingRequest.class).equals(payload));
	}
	
	@Test (priority = 7)
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
	
	
	private void validateResponse(Response res, CreateBookingRequest payload, String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"), payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"), payload.getDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"), payload.getBookingdates().getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"), payload.getBookingdates().getCheckout());
		Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"), payload.getAdditionalneeds());
	}
}

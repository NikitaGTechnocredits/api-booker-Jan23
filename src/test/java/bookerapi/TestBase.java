package bookerapi;

import org.testng.Assert;

import io.restassured.response.Response;
import pojo.Bookingdates;
import pojo.CreateBookingRequest;
import restCommon.PredefinedMethods;

public class TestBase {
	static String token;
	static int bookingId;
	static CreateBookingRequest payload;
	
	void init() {
		token = PredefinedMethods.createToken();
		
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
	}
	
	protected void validateResponse(Response res, CreateBookingRequest payload, String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"), payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"), payload.getDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"), payload.getBookingdates().getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"), payload.getBookingdates().getCheckout());
		Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"), payload.getAdditionalneeds());
	}
}

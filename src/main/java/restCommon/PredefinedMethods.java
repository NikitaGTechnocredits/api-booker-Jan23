package restCommon;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PredefinedMethods {
	
	public static String createToken() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}")
				.when().post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		return res.jsonPath().getString("token");
	}

}

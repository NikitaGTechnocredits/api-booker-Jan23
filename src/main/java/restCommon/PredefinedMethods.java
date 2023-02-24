package restCommon;

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
		if(res.statusCode() == 200)
			return res.jsonPath().getString("token");
		else
			return null;	
	}

}

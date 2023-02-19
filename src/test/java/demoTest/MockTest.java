package demoTest;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MockTest {

	@Test
	public void phoneNumbersTypeTest() {	
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io";
		
		Response res = RestAssured.given()
			.headers("Accept", "application/json")
			.when()
			.get("/test");
		
		System.out.println(res.asPrettyString());
		List<String> listOfType = res.jsonPath().getList("phoneNumbers.type");
		System.out.println(listOfType);
	}
	
	@Test
	public void phoneNumbersTest() {	
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io";
		
		Response res = RestAssured.given()
			.headers("Accept", "application/json")
			.when()
			.get("/test");
		
		//System.out.println(res.asPrettyString());
		List<Object> listOfPhoneNos = res.jsonPath().getList("phoneNumbers");
		//System.out.println(listOfPhoneNos);
		
		Map<String,String> mapOfPhoneNos = (Map<String,String>)listOfPhoneNos.get(0);
		System.out.println(mapOfPhoneNos);
		
	}
}

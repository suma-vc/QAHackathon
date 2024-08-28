package qaHackathon;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class APIAutomation {public static Integer sendPostRequest(String baseURI, String endpoint, String name, String description, double price, String itemType) {
	RestAssured.baseURI = baseURI;
	 
    String requestBody = String.format(
            "{\n" +
            "    \"name\": \"%s\",\n" +
            "    \"description\": \"%s\",\n" +
            "    \"price\": %.2f,\n" +
            "    \"item_type\": \"%s\"\n" +
            "}", name, description, price, itemType);

    Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post(endpoint);

    if (response.getStatusCode() == 200) {
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        return response.jsonPath().getInt("id");
    } else {
        System.out.println("Failed to send data to API. Status code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        return null;
    }
}
public static void validateData(Integer itemId, String endpoint) {
    if (itemId == null) {
        System.out.println("Item ID is null, cannot validate data.");
        return;
    }

    RestAssured.baseURI = "http://ec2-54-254-162-245.ap-southeast-1.compute.amazonaws.com:9000";

    Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .get(endpoint + itemId);

    if (response.getStatusCode() == 200) {
        System.out.println("Data validation successful for Item ID: " + itemId);
        System.out.println("Response Body: " + response.getBody().asString());
    } else {
        System.out.println("Data validation failed for Item ID: " + itemId);
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
has context menu}

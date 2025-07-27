import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyRestAssured {
    Properties prop;
    FileInputStream fs;

    public  MyRestAssured() throws IOException {
        prop = new Properties();
        fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }

    @Test
    public void login() throws ConfigurationException {
        RestAssured.baseURI = "http://dmoney.roadtocareer.net";

        String requestBody = "{\n" +
                "  \"email\": \"admin@roadtocareer.net\",\n" +
                "  \"password\": \"1234\"\n" +
                "}";

        Response res = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/user/login")
                .then()
                .assertThat().statusCode(200)
                .extract().response();

        System.out.println("Login Response:\n" + res.asString());

        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.getString("token");
        System.out.println("Token: " + token);

        Utils.setEnvVar("token", token); // Make sure Utils writes it to config.properties
    }

    @Test
    public void searchUser() throws IOException {


        RestAssured.baseURI = "http://dmoney.roadtocareer.net";
        Response res = given()
                .contentType("application/json")
                .header("Authorization", "bearer " + prop.getProperty("token"))
                .when()
                .get("/user/search/id/82029") // Removed space after `/id/`
                .then()
                .assertThat().statusCode(200)
                .extract().response();

        System.out.println("Search User Response:\n" + res.asString());
    }

    @Test
    public void createNewUser() throws IOException {

        RestAssured.baseURI= "http://dmoney.roadtocareer.net";
        Response res= given().contentType("application/json").body("{\n" +
                        "    \"name\":\"sanjida test user 1\",\n" +
                        "    \"email\":\"restassureduser1@test.com\",\n" +
                        "    \"password\":\"1234\",\n" +
                        "    \"phone_number\":\"01504450444\",\n" +
                        "    \"nid\":\"124156789\",\n" +
                        "    \"role\":\"customer\"\n" +
                        "}")
                .header("Authorization","bearer "+ prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("secretKey"))
                .when().post("/user/create")
                .then().assertThat().statusCode(201).extract().response();

        System.out.println(res.asString());
    }
    }


import io.restassured.RestAssured;
import io.restassured.response.Response;


import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public  UserController(Properties prop){
    this.prop=prop;
    RestAssured.baseURI= prop.getProperty("baseUrl");
    }
    public Response login() {


        String requestBody = "{\n" +
                "  \"email\": \"admin@roadtocareer.net\",\n" +
                "  \"password\": \"1234\"\n" +
                "}";

        return given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/user/login");
    }

    public Response searchUser(String userId){

        return given().contentType("application/json")
                .header("Authorization","bearer "+prop.getProperty("token"))
                .when().get("/user/search/id/"+userId);
    }
    public Response createUser(UserModel userModel){

        return given().contentType("application/json").body(userModel)
                .header("Authorization","bearer "+ prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("secretKey"))
                .when().post("/user/create");
    }
    public Response deleteUser(String userId){

        return given().contentType("application/json")
                .header("Authorization","bearer "+prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("secretKey"))
                .when().delete("/user/delete/"+userId);
    }


}

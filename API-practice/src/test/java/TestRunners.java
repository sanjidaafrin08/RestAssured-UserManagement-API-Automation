import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestRunners extends Setup {

    @Test(priority = 1, description = "user login")
    public void login() {
        UserController userController = new UserController(prop);
        Response res = userController.login();
        System.out.println(res.asString());

        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);

        try {
            Utils.setEnvVar("token", token); // Token save to config.properties
        } catch (ConfigurationException e) {
            e.printStackTrace(); // Show error in console
        }
    }

    @Test(priority = 2, description = "Search user by id")
    public void searchUser() {
        UserController userController = new UserController(prop);
        Response res = userController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        String messageActual =jsonPath.get("message");
        String messageExpected="User found";
        Assert.assertTrue(messageActual.contains(messageExpected));
        int id = jsonPath.get("user.id");
        String email = jsonPath.get("user.email");
        System.out.println(id + " " + email);
    }

    @Test(priority = 3, description = "Create new user")
    public void createUser() throws ConfigurationException {
        UserController userController=new UserController(prop);
        UserModel model=new UserModel();
        Faker faker=new Faker();
        model.setName("Rest Assured "+ faker.name().firstName());
        model.setEmail(faker.internet().emailAddress().toLowerCase());
        model.setPassword("1234");
        model.setPhone_number("0150"+Utils.generateRandomId(1000000,9999999));
        model.setNid("123456789");
        model.setRole("Customer");
        Response res= userController.createUser(model);
        System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        String message=jsonPath.get("message");
        Assert.assertTrue(message.contains("User created"));

        int userId= jsonPath.get("user.id");
        Utils.setEnvVar("userId",String.valueOf(userId));
    }
    @Test(priority = 4, description = "Delete user")
    public void deleteUser() {
        UserController userController = new UserController(prop);
        Response res = userController.deleteUser(prop.getProperty("userId"));
        System.out.println(res.asString());
    }

    @AfterMethod
    public  void  delay() throws InterruptedException {
        Thread.sleep(2000);
    }

}

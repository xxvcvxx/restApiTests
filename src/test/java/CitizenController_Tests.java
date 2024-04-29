import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CitizenController_Tests {
    String newName = "nowe";
    int id = 50;

    @Test
    public void testPOST() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"id\": 50, \"name\": \"Olg\", \"lastName\": \"Olg\", \"pesel\": \"99101011111\", \"dateOfBirth\": \"10-10-1990\"}");
        Response response = (Response) request.post(TestBase.citizenControllerUrl, new Object[0]);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeCreated);
        String bla = response.path("name");
        Assert.assertEquals(bla, "Olg");
    }

    @Test
    public void testPUT() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"id\": "+id+", \"name\": \"" + newName + "\", \"lastName\": \"Olga\", \"pesel\": \"99101011111\", \"dateOfBirth\": \"10-10-1990\"}");
        Response response = (Response) request.put(TestBase.citizenControllerUrl, new Object[0]);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        String actualName = response.path("name");
        Assert.assertEquals(actualName, newName);
        int actualId = response.path("id");
        Assert.assertEquals(actualId,id);
    }

    @Test
    public void testGET() {
        Response response = RestAssured.get(TestBase.citizenControllerUrl + "/50");
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
    }


}

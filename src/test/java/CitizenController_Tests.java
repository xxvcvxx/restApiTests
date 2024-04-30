import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = "FromPostToDeleteTests")
public class CitizenController_Tests {
    String newName = "noweimie";
    int id = 0;

    @Test(priority = 1)
    public void testPOST() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"id\": "+id+", \"name\": \"Temporary\", \"lastName\": \"Olg\", \"pesel\": \"99101011111\", \"dateOfBirth\": \"10-10-1990\"}");
        Response response = (Response) request.post(TestBase.citizenControllerUrl, new Object[0]);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeCreated);
        String actualName = response.path("name");
        id = response.path("id");
        Assert.assertEquals(actualName, "Temporary");
        System.out.println(id);
    }

    @Test(priority = 2)
    public void testPUT() {
        Assert.assertNotEquals(id, 0);
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

    @Test(priority = 3)
    public void testGET() {
        Assert.assertNotEquals(id, 0);
        Response response = RestAssured.get(TestBase.citizenControllerUrl + "/"+id);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("name"),newName);
    }@Test(priority = 4)
    public void testDELETE() {
        Assert.assertNotEquals(id, 0);
        Response response = RestAssured.delete(TestBase.citizenControllerUrl + "/"+id);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
    }

}

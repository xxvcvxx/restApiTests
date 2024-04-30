import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = "FromPostToDeleteTests")
public class CitizenController_Tests {
    private final String name = "Temporary";
    private final String newName = "noweimie";
    private int id = 0;

    @Test(priority = 0)
    public void testPOST() {
        // Arrange
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"id\": " + id + ", \"name\": \"" + name + "\", \"lastName\": \"Olg\", \"pesel\": \"99101011111\", \"dateOfBirth\": \"10-10-1990\"}");

        // Act
        Response response = request.post(TestBase.citizenControllerUrl);
        id = response.path("id");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeCreated);
        Assert.assertEquals(response.path("name"), name);
    }

    @Test(priority = 1)
    public void testPUT() {
        // Arrange
        Assert.assertNotEquals(id, 0); // The test will not pass if "testPost" is not executed first
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"id\": " + id + ", \"name\": \"" + newName + "\", \"lastName\": \"Olga\", \"pesel\": \"99101011111\", \"dateOfBirth\": \"10-10-1990\"}");

        // Act
        Response response = request.put(TestBase.citizenControllerUrl);
        int actualId = response.path("id");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("name"), newName);
        Assert.assertEquals(actualId, id);
    }

    @Test(priority = 2)
    public void testGET() {
        // Arrange
        Assert.assertNotEquals(id, 0); // The test will not pass if "testPost" is not executed first

        // Act
        Response response = RestAssured.get(TestBase.citizenControllerUrl + "/" + id);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("name"), newName);
    }

    @Test(priority = 3)
    public void testDELETE() {
        // Arrange
        Assert.assertNotEquals(id, 0); // The test will not pass if "testPOST" is not executed first

        // Act
        Response response = RestAssured.delete(TestBase.citizenControllerUrl + "/" + id);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
    }
}
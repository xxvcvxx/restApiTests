import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PeselController_TestPeselGenerateGetPost {

    @Test
    public static void peselGenerateGetTest() {

        for (int i = 0; i < 10; i++)//Sometimes generates a PESEL with an invalid length
        {
            Response response = RestAssured.get(TestBase.peselGenerateUrl);
            System.out.println(response.getStatusCode());
            Assert.assertEquals(response.getStatusCode(), TestBase.statusCodeSuccessOK);
        }
    }

    @Test(dataProvider = "dateOfBirth")
    public static void peselGeneratePostTest(String date, String gender, String expectedPeselprefix) {
        // Arrange
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json", new Object[0]);
        request.body("{\"dateOfBirth\": \""+date+"\", \"gender\": \""+gender+"\"}");

        // Act
        Response response = (Response) request.post(TestBase.peselGenerateUrl, new Object[0]);

        // Assert
        String pesel = response.asPrettyString();
        Assert.assertEquals(pesel.substring(0, 6), expectedPeselprefix);

    }

    @DataProvider
    public Object[][] dateOfBirth() {
        return new Object[][]{
                {"11-11-1999", "FEMALE","991111"},
                {"02-01-1988", "FEMALE","880102"},
                {"11-11-2001", "MALE","013111"},
        };
    }
}

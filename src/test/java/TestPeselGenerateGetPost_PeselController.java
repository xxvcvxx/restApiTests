import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestPeselGenerateGetPost_PeselController {

    @Test
    public static void peselGenerateGetTest() {

        for (int i = 0; i < 10; i++)//Sometimes generates a PESEL with an invalid length
        {
            Response response = RestAssured.get(TestBase.peselGenerateUrl);
            System.out.println(response.getStatusCode());
            //System.out.println(response.asPrettyString());
            Assert.assertEquals(response.getStatusCode(), TestBase.statusCodeSuccessOK);
        }
    }

    @Test(dataProvider = "dateOfBirth")
    public static void peselGeneratePostTest(String date, String gender, String expectedPeselprefix) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json", new Object[0]);
        request.body("{\"dateOfBirth\": \""+date+"\", \"gender\": \""+gender+"\"}");
        Response response = (Response) request.post(TestBase.peselGenerateUrl, new Object[0]);
        String pesel = response.asPrettyString();
        Assert.assertEquals(expectedPeselprefix, pesel.substring(0, 6));

    }

    @DataProvider
    public Object[][] dateOfBirth() {
        return new Object[][]{
                {"11-11-1999", "FEMALE","991111"},
                {"02-01-1988", "FEMALE","880102"},
                {"11-11-2001", "MALE","013111"},
                {"12-20-2004", "MALE","043220"},
        };
    }
}

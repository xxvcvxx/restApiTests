import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class TestTest {

    @Test
    public void testGetRequest_ResponseStatusCodeOk() {
        Response response = RestAssured.get("http://pesel.eu-central-1.elasticbeanstalk.com/api/v1/pesel/generate");
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.getTime());
        System.out.println(response.getBody());
        System.out.println(response.asPrettyString());
    }
}

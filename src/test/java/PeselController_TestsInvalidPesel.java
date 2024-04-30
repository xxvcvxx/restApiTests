import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PeselController_TestsInvalidPesel {

    @Test(dataProvider = "wrongLength")
    public static void invalidPeselTestWrongLength(String pesel) {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);

        // Assert
        String actualResponse = response.asPrettyString();
        String expectedResponse = "Invalid PESEL length";
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    @Test(dataProvider = "wrongLength")
    public static void invalidPeselTestWrongLengthStatus(String pesel) {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeBadRequest);
    }

    @Test
    public static void invalidPeselTestInvalidCharacter() {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + "1231$11A111");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeBadRequest);//500
    }

    @Test
    public static void invalidPeselTestWrongControlDigit() {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + "25040473911");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeBadRequest);//200 + no response body
    }

    @Test(dataProvider = "InvalidMonth")
    public static void invalidPeselTestInvalidMonth(String pesel, String description) {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeBadRequest);//500
    }

    @Test(dataProvider = "InvalidDay")
    public static void invalidPeselTestInvalidDay(String pesel, String description) {
        // Act
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);//500
    }

    @DataProvider
    public Object[][] wrongLength() {
        return new Object[][]{
                {"43891791" },
                {"1906056377667" },

        };
    }

    @DataProvider
    public Object[][] InvalidMonth() {
        return new Object[][]{
                {"59732854784", "Month = 73. " },
                {"03801450120", "Month = 80. " },
                {"73932079842", "Month = 93. " },

        };
    }

    @DataProvider
    public Object[][] InvalidDay() {
        return new Object[][]{
                {"32693133424", "Day = 31,month = 69. " },
                {"77900009626", "Day = 00,month = 90. " },
                {"92903209224", "Day = 32,month = 90. " },
                {"13110004527", "Day = 00,month = 11. " },
                {"62113158900", "Day = 31,month = 11. " },
                {"70520016245", "Day = 00,month = 52. " },
                {"31523229465", "Day = 32,month = 52. " },
        };
    }
}

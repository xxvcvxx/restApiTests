import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PeselValidator_Tests {

    @Test(dataProvider = "validPesels")
    public void validPeselTest(String pesel, String gender) {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), true);
        Assert.assertEquals(response.path("gender"), gender);
    }

    @Test(dataProvider = "invalidLength")
    public void invalidTempestInvalidLength(String pesel) {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), false);
        Assert.assertEquals(response.path("errors[0].errorCode"), "INVL");
        Assert.assertEquals(response.path("errors[0].errorMessage"), "Invalid length. Pesel should have exactly 11 digits.");
    }

    @Test
    public void invalidPeselTestEmptyRequest() {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + "");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeBadRequest);
    }

    @Test
    public void invalidPeselTestInvalidCharacter() {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + "98D@3457451");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), false);
        Assert.assertEquals(response.path("errors[0].errorCode"), "NBRQ");
        Assert.assertEquals(response.path("errors[0].errorMessage"), "Invalid characters. Pesel should be a number.");
    }

    @Test
    public void invalidPeselTestWrongControlDigit() {
        {
            // Act
            Response response = RestAssured.get(TestBase.peselvalidatorURL + "25040473911");

            // Assert
            Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
            Assert.assertEquals(response.path("isValid"), false);
            Assert.assertEquals(response.path("errors[0].errorCode"), "INVC");
            Assert.assertEquals(response.path("errors[0].errorMessage"), "Check sum is invalid. Check last digit.");
        }
    }

    @Test(dataProvider = "invalidMonth")
    public void invalidPeselTestInvalidMonth(String pesel) {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), false);
        Assert.assertEquals(response.path("errors[1].errorCode"), "INVM");
        Assert.assertEquals(response.path("errors[1].errorMessage"), "Invalid month.");
        Assert.assertNull(response.path("dateOfBrith"));
    }

    @Test(dataProvider = "invalidDay")
    public void invalidPeselTestInvalidDay(String pesel) {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + pesel);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), false);
        Assert.assertEquals(response.path("errors[0].errorCode"), "INVD");
        Assert.assertEquals(response.path("errors[0].errorMessage"), "Invalid day.");
        Assert.assertNull(response.path("dateOfBrith"));
    }

    @Test
    public void invalidPeselAllErrors() {
        // Act
        Response response = RestAssured.get(TestBase.peselvalidatorURL + "03803250120");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("isValid"), false);
        Assert.assertEquals(response.path("errors[0].errorCode"), "INVC");
        Assert.assertEquals(response.path("errors[0].errorMessage"), "Check sum is invalid. Check last digit.");
        Assert.assertEquals(response.path("errors[1].errorCode"), "INVY");
        Assert.assertEquals(response.path("errors[1].errorMessage"), "Invalid year.");
        Assert.assertEquals(response.path("errors[2].errorCode"), "INVM");
        Assert.assertEquals(response.path("errors[2].errorMessage"), "Invalid month.");
        Assert.assertEquals(response.path("errors[3].errorCode"), "INVD");
        Assert.assertEquals(response.path("errors[3].errorMessage"), "Invalid day.");
    }

    @DataProvider
    public Object[][] invalidLength() {
        return new Object[][]{
                {"438917"},
                {"1"},
                {"09230279273345"},
                {"15440140696345345345345323523"},
        };
    }

    @DataProvider
    public Object[][] validPesels() {
        return new Object[][]{
                {"43891791358", "Male"},// Male born between 1800-1899
                {"19060563776", "Male"},// Male born between 1900-1999
                {"09230279273", "Male"},// Male born between 2000-2099
                {"15440140696", "Male"},// Male born between 2100-2199
                {"43691591716", "Male"},// Male born between 2200-2299
                {"41861632784", "Female"},// Female born between 1800-1899
                {"21011916687", "Female"},// Female born between 1900-1999
                {"10323039821", "Female"},// Female born between 2000-2099
                {"03442825002", "Female"},// Female born between 2100-2199
                {"62722987126", "Female"} // Female born between 2200-2299
        };
    }

    @DataProvider
    public Object[][] invalidMonth() {
        return new Object[][]{
                {"93732854788"},// "Month = 73. "
                {"03801450120"},// "Month = 80. "
                {"73932079842"},// "Month = 93. "
        };
    }

    @DataProvider
    public Object[][] invalidDay() {
        return new Object[][]{
                {"32693133424"},// Day = 31,month = 69.
                {"77900009626"},// Day = 00,month = 90.
                {"92903209224"},// Day = 32,month = 90.
                {"13110004527"},// Day = 00,month = 11.
                {"62113158900"},// Day = 31,month = 11.
                {"70520016245"},// Day = 00,month = 52.
                {"31523229465"},// Day = 32,month = 52.
        };
    }
}


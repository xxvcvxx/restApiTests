import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PeselController_TestsValidPesel {

    @DataProvider
    public Object[][] validPesels() {
        return new Object[][]{
                {"43891791358", "Male 17-09-1843"},//Male born between 1800-1899
                {"19060563776", "Male 05-06-1919"},//Male born between 1900-1999
                {"09230279273", "Male 02-03-2009"},//Male born between 2000-2099
                {"15440140696", "Male 01-04-2115"},//Male born between 2100-2199
                {"43691591716", "Male 15-09-2243"},//Male born between 2200-2299
                {"41861632784", "Female 16-06-1841"},//Female born between 1800-1899
                {"21011916687", "Female 19-01-1921"},//Female born between 1900-1999
                {"10323039821", "Female 30-12-2010"},//Female born between 2000-2099
                {"03442825002", "Female 28-04-2103"},//Female born between 2100-2199
                {"62722987126", "Female 29-12-2262"} //Female born between 2200-2299
        };
    }

    @Test(dataProvider = "validPesels")
    public void validPeselTestStatusCodeCheck(String pesel, String description) {
        //System.out.println("Test case: "+description+" - "+ pesel);
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
    }

    @Test(dataProvider = "validPesels")
    public void validPeselTestResponseBody(String pesel, String description) {
        //System.out.println("Test case: "+description+" - "+ pesel);
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);
        boolean peselValid = response.path("peselValid");
        Assert.assertTrue(peselValid);
    }

    @Test(dataProvider = "validPesels")
    public void validPeselGender(String pesel, String description) {
        //System.out.println("Test case: "+description+" - "+ pesel);
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);
        String[] words = description.split("\\s+");
        String expectedGender = words[0].toUpperCase();
        String actualGender = response.path("gender");
        Assert.assertEquals(actualGender, expectedGender);
    }

    @Test(dataProvider = "validPesels")
    public void validPeselDateOfBirth(String pesel, String description) {
        //System.out.println("Test case: "+description+" - "+ pesel);
        Response response = RestAssured.get(TestBase.peselApiUrl + pesel);
        String[] words = description.split("\\s+");
        String expectedDateOfBirth = words[1];
        String actualDateOfBirth = response.path("dateOfBirth");
        Assert.assertEquals(actualDateOfBirth, expectedDateOfBirth);
    }
}

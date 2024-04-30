import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class NbpApiTest {
    @Test
    public void testEuroCurrency() {
        // Act
        Response response = RestAssured.get("http://api.nbp.pl/api/exchangerates/rates/A/EUR/today/");

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertEquals(response.path("currency"), "euro");
        Assert.assertEquals(response.path("rates[0].effectiveDate"), java.time.LocalDate.now().toString());
    }

    @Test
    public void testGoldPriceIsNotNull() {
        // Act
        Response response = RestAssured.get("http://api.nbp.pl/api/cenyzlota");
        ArrayList<Float> prices = response.path("cena");
        float actualPrice = prices.get(0);

        // Assert
        Assert.assertEquals(response.statusCode(), TestBase.statusCodeSuccessOK);
        Assert.assertNotEquals(actualPrice, null);
        Assert.assertNotEquals(actualPrice, 0);
    }
}
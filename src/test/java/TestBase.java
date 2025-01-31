import org.testng.Assert;

public class TestBase {
    //region pesel.eu-central
    public final static String peselApiUrl = "http://pesel.eu-central-1.elasticbeanstalk.com/api/v1/pesel/";
    public final static String peselGenerateUrl = "http://pesel.eu-central-1.elasticbeanstalk.com/api/v1/pesel/generate";
    public final static String citizenControllerUrl = "http://pesel.eu-central-1.elasticbeanstalk.com/api/v1/citizens";
    //endregion

    //region peselvalidatorapitest
    public final static String peselvalidatorURL = "https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=";
    //endregion
    public final static int statusCodeSuccessOK = 200;
    public final static int statusCodeBadRequest = 400;
    public final static int statusCodeInternalServerError = 500;
    public final static int statusCodeCreated = 201;
}
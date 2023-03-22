package CreateBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class createJSONBooking {
    HttpResponse<String> response;
    @Parameters({"url"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void createJSONAPI(String url) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{\r\n    \"firstname\" : \"Jim\",\r\n    \"lastname\" : \"Brown\",\r\n    \"totalprice\" : 111,\r\n    \"depositpaid\" : true,\r\n    \"bookingdates\" : {\r\n        \"checkin\" : \"2018-01-01\",\r\n        \"checkout\" : \"2019-01-01\"\r\n    },\r\n    \"additionalneeds\" : \"Breakfast\"\r\n}")
                .asString();
        String json = response.getBody();
        System.out.println(json);
    }
    @Test(groups = { "DevInt", "PrePro" })
    public void validateStatusCode(){
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.getStatusText());
    }
}

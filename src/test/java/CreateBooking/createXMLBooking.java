package CreateBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class createXMLBooking {
    HttpResponse<String> response;
    @Parameters({"url"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void createXMLAPI(String url) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.post(url)
                .header("Content-Type", "text/xml")
                .header("Accept", "application/xml")
                .body("<booking>\r\n    <firstname>Jim</firstname>\r\n    <lastname>Brown</lastname>\r\n    <totalprice>111</totalprice>\r\n    <depositpaid>true</depositpaid>\r\n    <bookingdates>\r\n      <checkin>2018-01-01</checkin>\r\n      <checkout>2019-01-01</checkout>\r\n    </bookingdates>\r\n    <additionalneeds>Breakfast</additionalneeds>\r\n  </booking>")
                .asString();
        String xml = response.getBody();
        System.out.println(xml);
    }
    @Test(groups = {"Pro"} )
    public void validateStatusCode(){
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.getStatusText());
    }
}

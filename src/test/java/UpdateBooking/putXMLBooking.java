package UpdateBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

public class putXMLBooking {
    HttpResponse<String> response;
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void putXMLBooking() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.put("https://restful-booker.herokuapp.com/booking/2")
                .header("Content-Type", "text/xml")
                .header("Accept", "application/xml")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body("<booking>\r\n    <firstname>Thuy</firstname>\r\n    <lastname>Dinh</lastname>\r\n    <totalprice>111</totalprice>\r\n    <depositpaid>true</depositpaid>\r\n    <bookingdates>\r\n      <checkin>2018-01-01</checkin>\r\n      <checkout>2019-01-01</checkout>\r\n    </bookingdates>\r\n    <additionalneeds>Breakfast</additionalneeds>\r\n  </booking>")
                .asString();
        String xml = response.getBody();
        System.out.println(xml);

    }
    @Test(groups = {"DevInt","PrePro","Pro"} )
    public void validateStatusCode(){
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.getStatusText());
    }
    @Test(groups = {"PrePro","Pro"} )
    public void validateXMLBooking(){
        JSONObject json= XML.toJSONObject(response.getBody());
        System.out.println(json);
        String firstName = (String) json.getJSONObject("booking").get("firstname");
        System.out.println(firstName);
        Assert.assertEquals(firstName,"Thuy");
        String lastName = (String) json.getJSONObject("booking").get("lastname");
        System.out.println(lastName);
        Assert.assertEquals(lastName,"Dinh");
        String checkin = (String) json.getJSONObject("booking").getJSONObject("bookingdates").get("checkin");
        System.out.println(checkin);
        Assert.assertEquals(checkin,"2018-01-01");
        String checkout = (String) json.getJSONObject("booking").getJSONObject("bookingdates").get("checkout");
        System.out.println(checkout);
        Assert.assertEquals(checkout,"2019-01-01");
        String additionalneeds = (String) json.getJSONObject("booking").get("additionalneeds");
        System.out.println(additionalneeds);
        Assert.assertEquals(additionalneeds,"Breakfast");
        Boolean depositpaid = (Boolean) json.getJSONObject("booking").get("depositpaid");
        System.out.println(depositpaid);
        Assert.assertEquals(Optional.of(depositpaid), Optional.of(true));
        int totalprice = (int) json.getJSONObject("booking").get("totalprice");
        System.out.println(totalprice);
        Assert.assertEquals(totalprice,111);
    }
}

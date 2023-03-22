package UpdateBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Optional;

public class putJSONBooking {
    HttpResponse<String> response;
    HttpResponse<JsonNode> request;
    SoftAssert softAssert = new SoftAssert();
    @Parameters({"url","id"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void putJSONBooking(String url, String id) throws UnirestException {
        SoftAssert softAssert = new SoftAssert();
        Unirest.setTimeouts(0, 0);
        response = Unirest.put(url+id)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=abc123")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body("{\r\n    \"firstname\" : \"Thuy\",\r\n    \"lastname\" : \"Dinh\",\r\n    \"totalprice\" : 111,\r\n    \"depositpaid\" : true,\r\n    \"bookingdates\" : {\r\n        \"checkin\" : \"2018-01-01\",\r\n        \"checkout\" : \"2019-01-01\"\r\n    },\r\n    \"additionalneeds\" : \"Breakfast\"\r\n}")
                .asString();
        request = Unirest.put("https://restful-booker.herokuapp.com/booking/2")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=abc123")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body("{\r\n    \"firstname\" : \"Thuy\",\r\n    \"lastname\" : \"Dinh\",\r\n    \"totalprice\" : 111,\r\n    \"depositpaid\" : true,\r\n    \"bookingdates\" : {\r\n        \"checkin\" : \"2018-01-01\",\r\n        \"checkout\" : \"2019-01-01\"\r\n    },\r\n    \"additionalneeds\" : \"Breakfast\"\r\n}")
                .asJson();
        String jsonString = response.getBody();
        System.out.println(jsonString);
    }
    @Test(groups = {"DevInt","PrePro","Pro"} )
    public void validateStatusCode(){
        softAssert.assertEquals(200, response.getStatus());
        softAssert.assertEquals("OK", response.getStatusText());
    }
    @Test(groups = {"Pro"} )
    public void validateAPI(){
        JSONObject json = request.getBody().getObject();
        String firstName = (String) json.get("firstname");
        System.out.println(firstName);
        softAssert.assertEquals(firstName,"Thuy");
        String lastName = (String) json.get("lastname");
        System.out.println(lastName);
        softAssert.assertEquals(lastName,"Dinh");
        String checkin = (String) json.getJSONObject("bookingdates").get("checkin");
        System.out.println(checkin);
        softAssert.assertEquals(checkin,"2018-01-01");
        String checkout = (String) json.getJSONObject("bookingdates").get("checkout");
        System.out.println(checkout);
        softAssert.assertEquals(checkout,"2019-01-01");
        String additionalneeds = (String) json.get("additionalneeds");
        System.out.println(additionalneeds);
        softAssert.assertEquals(additionalneeds,"Breakfast");
        Boolean depositpaid = (Boolean) json.get("depositpaid");
        System.out.println(depositpaid);
        softAssert.assertEquals(Optional.of(depositpaid), Optional.of(true));
        int totalprice = (int) json.get("totalprice");
        System.out.println(totalprice);
        softAssert.assertEquals(totalprice,111);
        softAssert.assertAll();
    }
}

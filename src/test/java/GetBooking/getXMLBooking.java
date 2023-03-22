package GetBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class getXMLBooking {
    HttpResponse<String> response;
    @Parameters({"url","id"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void getAPI(String url, String id) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.get(url+id)
                .header("Accept", "application/xml").asString();
        String xml = response.getBody();
        System.out.println(xml);
    }

    @Test(groups = {"DevInt","PrePro","Pro"} )
    public void validateStatusCode(){
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.getStatusText());
    }

    @Test(groups = {"DevInt"} )
    public void validateAPI(){
        JSONObject json= XML.toJSONObject(response.getBody());
        System.out.println(json);
        String firstName = (String) json.getJSONObject("booking").get("firstname");
        System.out.println(firstName);
        Assert.assertEquals(firstName,"Mary");
        String lastName = (String) json.getJSONObject("booking").get("lastname");
        System.out.println(lastName);
        Assert.assertEquals(lastName,"Wilson");

    }

}

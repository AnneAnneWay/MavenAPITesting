package GetBooking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class getJSONBooking {
    HttpResponse<String> response ;
    HttpResponse<JsonNode> request;
    @Parameters({"url","id"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void getAPI(String url, String id) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.get(url+id)
                .header("Accept", "application/json").asString();
        request = Unirest.get(url+id)
                .header("Accept", "application/json").asJson();
        //System.out.println(response.getBody());
        String json = response.getBody();
        System.out.println(json);
    }
    @Test(groups = {"DevInt","PrePro","Pro"} )
    public void validateStatusCode(){
        Assert.assertEquals(200,response.getStatus());
        Assert.assertEquals("OK",response.getStatusText());
    }
    @Test(groups = {"DevInt"} )
    public void validateAPI(){
        JSONObject myObj = request.getBody().getObject();
        System.out.println(myObj);
        String firstName = (String) myObj.get("firstname");
        System.out.println(firstName);
        Assert.assertEquals(firstName,"Eric");
        String lastName = (String) myObj.get("lastname");
        System.out.println(lastName);
        Assert.assertEquals(lastName,"Jackson");
    }
}

package ValidateSchema.ValidateXMLSchema;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class validateXMLSchema {
    HttpResponse<String> response;
    @Parameters({"url","id"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void setup(String url, String id) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.get(url+id)
                .header("Accept", "application/xml").asString();
        //System.out.println(response.getBody());
        String xml = response.getBody();
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            file = new File("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateXMLSchema\\data.xml");
            fileOutputStream = new FileOutputStream(file);
            //create file if not exists
            if (!file.exists()) {
                file.createNewFile();
            }
            //fetch bytes from data
            byte[] bs = xml.getBytes();
            fileOutputStream.write(bs);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("File written successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    @Test(groups = {"PrePro"} )
    public void validateSchema(){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateXMLSchema\\data.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateXMLSchema\\data.xml")));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());

        }

    }

}

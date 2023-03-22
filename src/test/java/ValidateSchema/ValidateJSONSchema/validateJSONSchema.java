package ValidateSchema.ValidateJSONSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class validateJSONSchema {
    HttpResponse<String> response;
    @Parameters({"url","id"})
    @BeforeMethod(groups = {"DevInt","PrePro","Pro"} )
    public void setUp(String url, String id) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        response = Unirest.get(url+id)
                .header("Accept", "application/json").asString();
        System.out.println(response.getBody());
        String json = response.getBody();
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            file = new File("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateJSONSchema\\data.json");
            fileOutputStream = new FileOutputStream(file);
            //create file if not exists
            if (!file.exists()) {
                file.createNewFile();
            }
            //fetch bytes from data
            byte[] bs = json.getBytes();
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
    @Test(groups = {"DevInt","PrePro"} )
    public void validateJSONSchema() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V6);
        File jsonStream = new File("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateJSONSchema\\data.json");
        InputStream schemaStream = Files.newInputStream(Paths.get("C:\\Users\\dinh.thuy\\IdeaProjects\\MavenProject\\src\\test\\ValidateSchema\\ValidateJSONSchema\\schemaJSON.json"));
        JsonNode json = objectMapper.readTree(jsonStream);
        JsonSchema schema = schemaFactory.getSchema(schemaStream);
       // System.out.println(schema.toString());
        Set<ValidationMessage> validationResult = schema.validate(json);
        // print validation errors
        if (validationResult.isEmpty()) {
            System.out.println("no validation errors");
        } else {
            validationResult.forEach(vm -> System.out.println(vm.getMessage()));
        }
    }
}

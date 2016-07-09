import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import org.json.simple.JSONObject;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.Json;
import play.libs.F.*;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.twirl.api.Content;
import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest {

    
    @Test
    public void testReadResponse() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                System.out.println("-------------------->"+ response.getBody());
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    
    @Test
    public void testCreateResponse() throws Exception {
        TestServer server = testServer(3333);
        JSONObject obj = new JSONObject();
        obj.put("job_id", 8);
 		obj.put("customerName", "ikram");
 		obj.put("notes", "testnote");
 		obj.put("address", "myaddress");
        JsonNode node = Json.toJson(obj);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/").post(node);
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    
    @Test
    public void testUpdateResponse() throws Exception {
        TestServer server = testServer(3333);
        JSONObject obj = new JSONObject();
        obj.put("job_id", 7);
 		obj.put("customerName", "ikramUpdate");
 		obj.put("notes", "noteUpdate");
 		obj.put("address", "addressUpdate");
        JsonNode node = Json.toJson(obj);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/").put(node);
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    
    @Test
    public void testDeleteResponse() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/?job_id=4").delete();
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}

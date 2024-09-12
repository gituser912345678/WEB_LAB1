import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        var fcgiInterface = new FCGIInterface();

        while (fcgiInterface.FCGIaccept() >= 0) {

            final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
            final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
            final String RESULT_JSON = """
            {
                "hit": "%s",
                "exec_time": "%s"
            }
            """;
            final String ERROR_JSON = """
            {
                "reason": "%s"
            }
            """;

            var query = System.getProperties().getProperty("QUERY_STRING");

            var request = readRequestBody(query);
            long startTime = System.currentTimeMillis();

            String result = Script.defenitionHit(Integer.parseInt(request.get("x_field")), Integer.parseInt(request.get("y_field")), Float.parseFloat(request.get("R_field")));

            long endTime = System.currentTimeMillis();
            String executionTime = String.valueOf(endTime - startTime);

            var json = String.format(RESULT_JSON, result, executionTime);
            var response = String.format(HTTP_RESPONSE, json.getBytes(StandardCharsets.UTF_8).length + 2, json);
            System.out.println(response);



        }

    }
    private static Map<String, String> readRequestBody(String request) {
        var queryPairs = new HashMap<String, String>();
        var pairs = request.split("&");
        for (var pair : pairs) {
            var idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }
}
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;


public class HttpClientTest {
    private static void doGet() {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8808/test");
        int code = 0;
        try {
            code = client.executeMethod(getMethod);
            if (code == 200) {
                String res = getMethod.getResponseBodyAsString();
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        doGet();
    }
}
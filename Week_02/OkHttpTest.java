import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpTest {

    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OkHttpTest example = new OkHttpTest();
        String response = example.run("http://localhost:8808/test");
        System.out.println(response);
    }
}



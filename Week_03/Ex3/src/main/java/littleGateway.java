import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class littleGateway {
    private static URL url;
    private static String uri;

    private static void service(Socket socket) {
        try {
            if(filter(uri)) {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Content-Type:text/html;charset=utf-8");
                String body = getBody();
                printWriter.println("Content-Length:" + body.getBytes().length);
                printWriter.println();
                printWriter.write(body);
                printWriter.close();
                socket.close();
            } else {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("HTTP/1.1 401 Unauthorized");
                printWriter.println("Content-Type:text/html;charset=utf-8");
                printWriter.println("Content-Length:" + 0);
                printWriter.println();
                printWriter.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getBody(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8088" + url.getPath());
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity !=null){
                return EntityUtils.toString(httpEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static boolean filter(String uri) {
        if (uri.contains("/admin")) {
            return false;
        }
        return uri.startsWith("/api");
    }

    /**
     *    localhost:8888 => localhost:8088/api/hello
	 *    只实现了在Java 运行参数输入网址，再访问localhost:8888
	 *    不知道如何获取get 的参数
     * @param args     localhost:8888/api/hello
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final ServerSocket serverSocket = new ServerSocket(8888);
        if(!args[0].startsWith("http://")) {
            url = new URL("http://"+args[0]);
        }else {
            url = new URL(args[0]);
        }
        uri = url.getPath();
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                executorService.execute(() -> service(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

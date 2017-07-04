import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.URL;

/**
 * Created by xdcao on 2017/7/4.
 */
public class Test1 {

    @Test
    public void test() throws IOException {
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet httpGet=new HttpGet("http://www.baidu.com");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity=httpResponse.getEntity();
        System.out.println(EntityUtils.toString(entity,"utf-8"));

    }

}

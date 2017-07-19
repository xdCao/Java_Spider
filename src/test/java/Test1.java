import jsoupDemo.ExtractService;
import jsoupDemo.LinkTypeData;
import jsoupDemo.Rule;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void test2(){
        Rule rule=new Rule("http://news.baidu.com/ns",new String[]{"word"},new String[]{"支付宝"},-1,null, jsoupDemo.Rule.GET);

        List<LinkTypeData> extracts= ExtractService.extract(rule);

        for (LinkTypeData data:extracts){
            System.out.println(data);
        }


    }

    @Test
    public void testList(){
        List<String> list=new ArrayList<String>();
        list.add("hah");
        list.add("hoho");
        System.out.println(list);
    }

    @Test
    public void testKedou() throws IOException {
        CloseableHttpClient httpClient=HttpClients.createDefault();
        String url="http://www.cao0003.com/get_file/3/93c77bf5f6ce7190502ebebe6c04b731/43000/43742/43742.mp4/";
        HttpGet httpGet=new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        byte[] bytes = EntityUtils.toByteArray(response.getEntity());
        System.out.println(bytes.length);
        File file=new File("D://43742.mp4");
        BufferedOutputStream stream=null;
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            stream=new BufferedOutputStream(fileOutputStream);
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            stream=null;
        }
    }


}

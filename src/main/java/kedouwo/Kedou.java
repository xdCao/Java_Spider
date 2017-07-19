package kedouwo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by xdcao on 2017/7/19.
 */
public class Kedou {

    private static final String path="G://vs";

    private static String url="http://www.cao0003.com";

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static Html getPage(String url) throws IOException {
        HttpGet httpGet=new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Html infoHtml=new Html(EntityUtils.toString(response.getEntity()),url);
//        System.out.print(infoHtml);
        return infoHtml;
    }

    public static List<String> getIndexVideoLinks(Html html){
        List<String> all = html.xpath("//div[@class='content']/div/div/div/div//a[@href]").links().regex("http://www\\.cao0003\\.com/videos/.*").all();
//        System.out.print(all);
        return all;
    }

    public static boolean getVideo(String videoPage) throws IOException {

        HttpGet httpGet=new HttpGet(videoPage);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Html html=new Html(EntityUtils.toString(response.getEntity()),videoPage);
        String videoLink = html.xpath("//div").links().regex(".*\\.mp4").get();
        if (downLoadVideo(videoLink)){
            return true;
        }else {
            return false;
        }

    }

    private static boolean downLoadVideo(String videoLink) {

        BufferedOutputStream stream=null;
        try {
            HttpGet httpGet=new HttpGet(videoLink);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            System.out.println(bytes.length);
            File file=new File(path,System.currentTimeMillis()+".mp4");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            stream=new BufferedOutputStream(fileOutputStream);
            stream.write(bytes);
            stream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            stream=null;
            return false;
        }

    }


    public static void main(String[] args) throws IOException {
        Html page = getPage(url);
        List<String> indexVideoLinks = getIndexVideoLinks(page);
        for (String videopage:indexVideoLinks){
            if (getVideo(videopage)){
                System.out.println("Success!");
            }else {
                System.out.println("Failure!");
            }
        }
//        System.out.print(indexVideoLinks.size());
    }
}

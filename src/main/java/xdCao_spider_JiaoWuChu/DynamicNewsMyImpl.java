package xdCao_spider_JiaoWuChu;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xdcao on 2017/7/26.
 */
public class DynamicNewsMyImpl {

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    public static void main(String[] args) throws IOException {
        crawAndSaveDynamicNews();
    }

    public static void crawAndSaveDynamicNews() throws IOException {

        HttpGet httpGet=new HttpGet("http://gr.xidian.edu.cn/zxdt/1.htm");
        CloseableHttpResponse resp = httpClient.execute(httpGet);
        Html page=new Html(EntityUtils.toString(resp.getEntity(),"utf-8"),"http://gr.xidian.edu.cn/zxdt/1.htm");

        List<String> linkList = page.links().regex("http://gr\\.xidian\\.edu\\.cn/zxdt.*\\.htm").all();
        Set<String> linkSet=new HashSet<String>(linkList);

        for (String link:linkSet){
            HttpGet httpGet1=new HttpGet(link);
            CloseableHttpResponse resp1 = httpClient.execute(httpGet1);
            Html page1=new Html(EntityUtils.toString(resp1.getEntity(),"utf-8"),link);

            List<String> allTitle = page1.xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
            List<String> allLinks = page1.xpath("//div[@class='main-right-list']/ul/li/a/@href").all();
            List<String> allDates = page1.xpath("//div[@class='main-right-list']/ul/li/span").all();

            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

            for (int i=0;i<allTitle.size();i++){
                try {
                    DynamicNews dynamicNews=new DynamicNews();
                    dynamicNews.setTitle(allTitle.get(i));
                    dynamicNews.setLink(allLinks.get(i).replaceAll(".*info","http://gr.xidian.edu.cn/info"));
                    dynamicNews.setDate(formatter.parse(allDates.get(i).substring(13, 23)));
                    HttpGet httpGet2=new HttpGet(dynamicNews.getLink());
                    CloseableHttpResponse response = httpClient.execute(httpGet2);
                    String content= EntityUtils.toString(response.getEntity(),"utf-8");
                    dynamicNews.setContent(content);
                    System.out.println("新增最新动态："+dynamicNews.getTitle());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }


    }

}

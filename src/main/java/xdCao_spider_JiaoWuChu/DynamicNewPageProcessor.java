package xdCao_spider_JiaoWuChu;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/7/9.
 */
public class DynamicNewPageProcessor implements PageProcessor {

    private Site site=Site.me().setRetryTimes(3).setSleepTime(100);

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    public void process(Page page) {

        page.addTargetRequests(page.getHtml().links().regex("http://gr\\.xidian\\.edu\\.cn/zxdt.*\\.htm").all());
        List<String> allTitle = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
        List<String> allLinks = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/@href").all();

        List<DynamicNews> newsList=new ArrayList<DynamicNews>();

        for (int i=0;i<allTitle.size();i++){
            try {
                DynamicNews dynamicNews=new DynamicNews();
                dynamicNews.setTitle(allTitle.get(i));
                dynamicNews.setLink(allLinks.get(i).replaceAll(".*info","http://gr.xidian.edu.cn/info"));
                HttpGet httpGet=new HttpGet(dynamicNews.getLink());
                CloseableHttpResponse response = httpClient.execute(httpGet);
                String content= EntityUtils.toString(response.getEntity(),"utf-8");
                dynamicNews.setContent(content);
                newsList.add(dynamicNews);
                System.out.println(dynamicNews);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args){
        Spider.create(new DynamicNewPageProcessor()).addUrl("http://gr.xidian.edu.cn/zxdt/1.htm").thread(5).run();
    }

}

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

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/7/9.
 */
public class NoticeInformationPageProcessor implements PageProcessor {



    private Site site=Site.me().setRetryTimes(3).setSleepTime(100);

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    public void process(Page page) {

        page.addTargetRequests(page.getHtml().links().regex("http://gr\\.xidian\\.edu\\.cn/tzgg1/.\\.htm").all());
        List<String> allTitle = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
        List<String> allLinks = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/@href").all();

//        System.out.println(allLinks);
        List<NoticeInformation> informationList=new ArrayList<NoticeInformation>();

        for (int i=0;i<allTitle.size();i++){
            try {
                NoticeInformation noticeInformation=new NoticeInformation();
                noticeInformation.setTitle(allTitle.get(i));
                noticeInformation.setLink(allLinks.get(i).replaceAll(".*info","http://gr.xidian.edu.cn/info"));
                HttpGet httpGet=new HttpGet(noticeInformation.getLink());
                CloseableHttpResponse response = httpClient.execute(httpGet);
                String content= EntityUtils.toString(response.getEntity(),"utf-8");
                noticeInformation.setContent(content);
                informationList.add(noticeInformation);
                System.out.println(noticeInformation);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args){
        Spider.create(new NoticeInformationPageProcessor()).addUrl("http://gr.xidian.edu.cn/tzgg1.htm").thread(5).run();
    }

}

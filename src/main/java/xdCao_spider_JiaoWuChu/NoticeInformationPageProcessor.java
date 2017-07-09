package xdCao_spider_JiaoWuChu;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by xdcao on 2017/7/9.
 */
public class NoticeInformationPageProcessor implements PageProcessor {

    private Site site=Site.me().setRetryTimes(3).setSleepTime(100);

    public void process(Page page) {

        page.addTargetRequests(page.getHtml().links().regex("http://gr\\.xidian\\.edu\\.cn/tzgg1/.\\.htm").all());
        List<String> allTitle = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
        List<String> allLinks = page.getHtml().xpath("//div[@class='main-right-list']/ul/li/a/@href").all();

//        System.out.println(allLinks);

        for (int i=0;i<allTitle.size();i++){
            NoticeInformation noticeInformation=new NoticeInformation();
            noticeInformation.setTitle(allTitle.get(i));
            noticeInformation.setLink(allLinks.get(i).replaceAll(".*info","http://gr.xidian.edu.cn/info"));
            System.out.println(noticeInformation.toString());
        }


    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args){
        Spider.create(new NoticeInformationPageProcessor()).addUrl("http://gr.xidian.edu.cn/tzgg1.htm").thread(5).run();
    }

}

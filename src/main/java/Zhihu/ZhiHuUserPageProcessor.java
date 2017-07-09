package Zhihu;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

/**
 * Created by xdcao on 2017/7/7.
 */
public class ZhiHuUserPageProcessor implements PageProcessor {

    private Site site=Site.me().setRetryTimes(10).setSleepTime(1000);

    private static int num=0;

    private static String keyword="JAVA";



    public void process(Page page) {

        //1. 如果是用户列表页面 【入口页面】，将所有用户的详细页面的url放入target集合中。
        if(page.getUrl().regex("https://www\\.zhihu\\.com/search\\?type=people&q=[\\s\\S]+").match()){
            page.addTargetRequests(page.getHtml().xpath("//ul[@class='list users']/li/div/div[@class='body']/div[@class='line']").links().all());
        }
        //2. 如果是用户详细页面
        else {
            num++;//用户数++
            /*实例化ZhihuUser，方便持久化存储。*/
            ZhiHuUser user = new ZhiHuUser();
            /*从下载到的用户详细页面中抽取想要的信息，这里使用xpath居多*/
            /*为了方便理解，抽取到的信息先用变量存储，下面再赋值给对象*/
            String name = page.getHtml().xpath("//div[@class='ProfileHeader-contentHead']/span[@class='ProfileHeader-name']").get();
            String identity = page.getHtml().xpath("//div[@class='title-section ellipsis']/span[@class='bio']/@title").get();
            String location = page.getHtml().xpath("//div[@class='item editable-group']/span[@class='info-wrap']/span[@class='location item']/@title").get();
            String profession = page.getHtml().xpath("//div[@class='item editable-group']/span[@class='info-wrap']/span[@class='business item']/@title").get();
            boolean isMale = page.getHtml().xpath("//span[@class='item gender']/i[@class='icon icon-profile-male']").match();
            boolean isFemale = page.getHtml().xpath("//span[@class='item gender']/i[@class='icon icon-profile-female']").match();

            int sex = -1;
            /*因为知乎有一部分人不设置性别 或者 不显示性别。所以需要判断一下。*/
            if (isMale && !isFemale)
                sex = 1;//1代表男性
            else if (!isMale && isFemale)
                sex = 0;//0代表女性
            else
                sex = 2;//2代表未知


            String school = page.getHtml().xpath("//span[@class='education item']/@title").get();
            String major = page.getHtml().xpath("//span[@class='education-extra item']/@title").get();
            String recommend = page.getHtml().xpath("//span[@class='fold-item']/span[@class='content']/@title").get();
            String picUrl = page.getHtml().xpath("//div[@class='UserAvatar ProfileHeader-avatar']/img[@class='Avatar Avatar--l']/@src").get();
//            int agree = Integer.parseInt(page.getHtml().xpath("//span[@class='zm-profile-header-user-agree']/strong/text()").get());
//            int thanks = Integer.parseInt(page.getHtml().xpath("//span[@class='zm-profile-header-user-thanks']/strong/text()").get());
//            int ask = Integer.parseInt(page.getHtml().xpath("//div[@class='profile-navbar clearfix']/a[2]/span[@class='num']/text()").get());
//            int answer = Integer.parseInt(page.getHtml().xpath("//div[@class='profile-navbar clearfix']/a[3]/span[@class='num']/text()").get());
//            int article = Integer.parseInt(page.getHtml().xpath("//div[@class='profile-navbar clearfix']/a[4]/span[@class='num']/text()").get());
//            int collection = Integer.parseInt(page.getHtml().xpath("//div[@class='profile-navbar clearfix']/a[5]/span[@class='num']/text()").get());

            //对象赋值
            user.setKey(keyword);
            user.setName(name);
            user.setIdentity(identity);
            user.setLocation(location);
            user.setProfession(profession);
            user.setSex(sex);
            user.setSchool(school);
            user.setMajor(major);
            user.setRecommend(recommend);
            user.setPicUrl(picUrl);
//            user.setAgree(agree);
//            user.setThanks(thanks);
//            user.setAsk(ask);
//            user.setAnswer(answer);
//            user.setArticle(article);
//            user.setCollection(collection);

            System.out.println("num:" + num + " " + user.toString());//输出对象

        }

    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args){
        long startTime ,endTime;
        System.out.println("========知乎用户信息小爬虫【启动】喽！=========");
        startTime = new Date().getTime();
        //入口为：【https://www.zhihu.com/search?type=people&q=xxx 】，其中xxx 是搜索关键词
        Spider.create(new ZhiHuUserPageProcessor()).addUrl("https://www.zhihu.com/search?type=people&q="+keyword).thread(5).run();
        endTime = new Date().getTime();
        System.out.println("========知乎用户信息小爬虫【结束】喽！=========");
        System.out.println("一共爬到"+num+"个用户信息！用时为："+(endTime-startTime)/1000+"s");
    }


}

package xdCao_spider_JiaoWuChu;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/7/10.
 */
public class LoginJiaoWuchu {


    public static final String authUrl="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp";

    public static final String loginUrl="http://jwxt.xidian.edu.cn/caslogin.jsp";

    private static CloseableHttpClient httpClient=HttpClients.createDefault();

    public static void main(String[] args) throws IOException {


        String cookie=login("1601120078","208037");

        getCourse(cookie);

        getInfo(cookie);

    }

    private static void getInfo(String cookie) throws IOException {

        HttpGet info=new HttpGet("http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        info.setHeader("Cookie",cookie);
        CloseableHttpResponse infoResp = httpClient.execute(info);
        Html infoHtml=new Html(EntityUtils.toString(infoResp.getEntity()),"http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        System.out.print(infoHtml.toString());

    }

    private static void getCourse(String cookie) throws IOException {

        HttpGet course=new HttpGet("http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
        course.setHeader("Cookie",cookie);
        CloseableHttpResponse courseResp = httpClient.execute(course);


        Html courseHtml=new Html(EntityUtils.toString(courseResp.getEntity()),"http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
        System.out.print(courseHtml.xpath("//div[@id='list']/table").all());

    }


    private static String login(String username, String password) throws IOException {

        String cookie=null;


        try {
            HttpGet httpGet=new HttpGet(authUrl);
            CloseableHttpResponse get1 = httpClient.execute(httpGet);
            String cookie0 = getCookieStoreFirstTime(get1);
            Html html1=new Html(EntityUtils.toString(get1.getEntity()),authUrl);
            String lt=html1.xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().substring("<input type=\"hidden\" name=\"lt\" value=\"".length(),html1.xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().length()-2);
            String exe=html1.xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().substring("<input type=\"hidden\" name=\"execution\" value=\"".length(),html1.xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().length()-2);


            HttpPost httpPost=new HttpPost(authUrl);
            List<NameValuePair> data=new ArrayList<NameValuePair>();

            data.add(new BasicNameValuePair("username",username));
            data.add(new BasicNameValuePair("password",password));
            data.add(new BasicNameValuePair("submit",""));
            data.add(new BasicNameValuePair("lt",lt));
            data.add(new BasicNameValuePair("execution",exe));
            data.add(new BasicNameValuePair("_eventId","submit"));
            data.add(new BasicNameValuePair("rmShown","1"));

            httpPost.setEntity(new UrlEncodedFormEntity(data));

            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
            httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Referer", "http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Cookie",cookie0);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            cookie=getCookieStoreFirstTime(httpResponse);

            HttpGet httpGet1=new HttpGet(loginUrl);
            httpGet.setHeader("Cookie",cookie);

            CloseableHttpResponse res = httpClient.execute(httpGet);

            return cookie;


        }catch (Exception e){
            e.printStackTrace();
        }

        return cookie;

    }



    public static void printResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        //判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:"
                    + responseString.replace("\r\n", ""));
        }
    }




    public static String getCookieStoreFirstTime(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");

        System.out.println(setCookies[0].getValue());
        System.out.println(setCookies[1].getValue());

        //ROUTE
        String route=setCookies[0].getValue();
//        System.out.println("route cookie: "+route);


        // JSESSIONID
        String JSESSIONID = setCookies[1].getValue();
//        System.out.println("JSESSIONID cookie:" + JSESSIONID);

        if (setCookies.length==2){
            return route+";"+JSESSIONID;
        }else {
            //BIGipServeridsnew.xidian.edu.cn
            String big=setCookies[2].getValue();
//        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

            return route+";"+JSESSIONID+" "+big;
        }




    }

}

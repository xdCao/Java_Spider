package xdCao_spider_JiaoWuChu;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xdcao on 2017/7/10.
 */
public class LoginProcessor implements PageProcessor {

    private String username;

    private String password;

    public LoginProcessor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static final String authUrl="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp";

    public static final String loginUrl="http://jwxt.xidian.edu.cn/caslogin.jsp";

    private Site site=Site.me();

    private static CookieStore cookieStore=null;

//    private CloseableHttpClient httpClient= HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    private CloseableHttpClient httpClient=HttpClients.createDefault();

    public static void main(String[] args){
        Spider.create(new LoginProcessor("1601120078","208037")).addUrl(authUrl).thread(1).run();
    }

    public void process(Page page) {



            try {

                List<String> list = page.getHeaders().get("Set-Cookie");
//                System.out.println(list.get(0));
//                System.out.println(list.get(1));
//                System.out.println(list.get(2));
                String cookie1=list.get(0)+"; "+list.get(1)+" "+list.get(2);

                String lt=page.getHtml().xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().substring("<input type=\"hidden\" name=\"lt\" value=\"".length(),page.getHtml().xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().length()-2);
                String exe=page.getHtml().xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().substring("<input type=\"hidden\" name=\"execution\" value=\"".length(),page.getHtml().xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().length()-2);
//                System.out.println(lt);
//                System.out.println(exe);

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
                httpPost.setHeader("Cookie",cookie1);

                CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//            printResponse(httpResponse);


                String cookie=getCookieStoreFirstTime2(httpResponse);
                List<String> cookieList=getCookieStoreFirstTime(httpResponse);
//                System.out.println(cookie);


                HttpGet httpGet=new HttpGet(loginUrl);
                httpGet.setHeader("Cookie",cookie);

                CloseableHttpResponse res = httpClient.execute(httpGet);
//            printResponse(res);

                HttpGet httpGet1=new HttpGet("http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
                httpGet1.setHeader("Cookie",cookie);
                CloseableHttpResponse execute = httpClient.execute(httpGet1);
//                printResponse(execute);

                Html html=new Html(EntityUtils.toString(execute.getEntity()),"http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
                System.out.print(html.xpath("//div[@id='list']/table").all());



            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



    }

    public Site getSite() {
        return site;
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

    public static List<String> getCookieStoreFirstTime(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");

        //ROUTE
        String route=setCookies[0].getValue();
//        System.out.println("route cookie: "+route);


        // JSESSIONID
        String JSESSIONID = setCookies[1].getValue();
//        System.out.println("JSESSIONID cookie:" + JSESSIONID);

        //BIGipServeridsnew.xidian.edu.cn
        String big=setCookies[2].getValue();
//        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

//        return route+" "+JSESSIONID+" "+big;

        List<String> list=new ArrayList<String>();
        list.add(route);
        list.add(JSESSIONID);
        list.add(big);

        return list;

    }


    public static String getCookieStoreFirstTime2(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");

        //ROUTE
        String route=setCookies[0].getValue();
//        System.out.println("route cookie: "+route);


        // JSESSIONID
        String JSESSIONID = setCookies[1].getValue();
//        System.out.println("JSESSIONID cookie:" + JSESSIONID);

        //BIGipServeridsnew.xidian.edu.cn
        String big=setCookies[2].getValue();
//        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

        return route+" "+JSESSIONID+" "+big;


    }

}

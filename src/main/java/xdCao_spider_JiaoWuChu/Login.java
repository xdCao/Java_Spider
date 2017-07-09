package xdCao_spider_JiaoWuChu;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/7/9.
 */
public class Login {

    static CookieStore cookieStore=null;
    static HttpClientContext context=null;

    private static CloseableHttpClient httpClient= HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    public void login() throws IOException {

        String loginUrl1="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fyjsxt.xidian.edu.cn%2Flogin.jsp";


        HttpPost httpPost1=new HttpPost(loginUrl1);

        List<NameValuePair> data=new ArrayList<NameValuePair>();

        data.add(new BasicNameValuePair("username","1601120078"));
        data.add(new BasicNameValuePair("password","208037"));


        CloseableHttpResponse httpResponse1 = httpClient.execute(httpPost1);
        printResponse(httpResponse1);
//        String newUrl=httpResponse1.getFirstHeader("Location").getValue();

        setCookieStoreFirstTime(httpResponse1);


//        HttpPost httpPost2=new HttpPost(dataUrl);
//        httpPost2.setEntity(new UrlEncodedFormEntity(data));
//        CloseableHttpResponse httpResponse2 = httpClient.execute(httpPost2);
//        printResponse(httpResponse2);

//        setCookieStoreSecondTime(httpResponse2);

        String url1="http://ids.xidian.edu.cn/authserver/login;jsessionid=w6EnuYfr0-iqgEyzfV0zxch8Ml4xHq7sBAy0GjnfyWTx5OZvs-74!-258421689?service=http%3A%2F%2Fyjsxt.xidian.edu.cn%2Flogin.jsp";
        HttpPost httpPost=new HttpPost(url1);
        httpPost.setEntity(new UrlEncodedFormEntity(data));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        printResponse(httpResponse);


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

    public static void setCookieStoreFirstTime(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");

        //ROUTE
        String route=setCookies[0].getValue().substring("route=".length(),setCookies[0].getValue().length());
        System.out.println("route cookie: "+route);


        // JSESSIONID
        String JSESSIONID = setCookies[1].getValue().substring("JSESSIONID=".length(),
                setCookies[1].getValue().indexOf(";"));
        System.out.println("JSESSIONID cookie:" + JSESSIONID);

        //BIGipServeridsnew.xidian.edu.cn
        String big=setCookies[2].getValue().substring("BIGipServeridsnew.xidian.edu.cn=".length(),setCookies[2].getValue().indexOf(";"));
        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

        // 新建一个Cookie
        BasicClientCookie cookie0 = new BasicClientCookie("route",
                route);
        cookie0.setVersion(0);
        cookie0.setDomain("127.0.0.1");
        cookie0.setPath("/CwlProClient");
        BasicClientCookie cookie1 = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie1.setVersion(0);
        cookie1.setDomain("127.0.0.1");
        cookie1.setPath("/CwlProClient");

        BasicClientCookie cookie2 = new BasicClientCookie("BIGipServeridsnew.xidian.edu.cn",
                big);
        cookie2.setVersion(0);
        cookie2.setDomain("127.0.0.1");
        cookie2.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie0);
        cookieStore.addCookie(cookie1);
        cookieStore.addCookie(cookie2);

        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            System.out.println("Local cookie: " + cookies.get(i));
        }

    }


    private void setCookieStoreSecondTime(CloseableHttpResponse httpResponse) {

        System.out.println("----setCookieStore");

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");

        //JSESSIONID
        String route=setCookies[0].getValue().substring("JSESSIONID=".length(),setCookies[0].getValue().length());
        System.out.println("JSESSIONID cookie: "+route);


        //BIGipServeridsnew.xidian.edu.cn
        String big=setCookies[1].getValue().substring("BIGipServeridsnew.xidian.edu.cn=".length(),setCookies[1].getValue().indexOf(";"));
        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

        // 新建一个Cookie
        BasicClientCookie cookie0 = new BasicClientCookie("JSESSIONID",
                route);
        cookie0.setVersion(0);
        cookie0.setDomain("127.0.0.1");
        cookie0.setPath("/CwlProClient");

        BasicClientCookie cookie1 = new BasicClientCookie("BIGipServeridsnew.xidian.edu.cn",
                big);
        cookie1.setVersion(0);
        cookie1.setDomain("127.0.0.1");
        cookie1.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie0);
        cookieStore.addCookie(cookie1);


        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            System.out.println("Local cookie: " + cookies.get(i));
        }

    }



    public static void setContext() {
        System.out.println("----setContext");
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider> create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
    }

    public static void main(String[] args) throws IOException {
        Login login=new Login();
        login.login();
    }

}

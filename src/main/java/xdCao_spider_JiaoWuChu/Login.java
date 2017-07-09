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

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    public void login() throws IOException {

        String loginUrl="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fyjsxt.xidian.edu.cn%2Flogin.jsp";
        String loginUrl2="http://jwxt.xidian.edu.cn/caslogin.jsp";
        String dataUrl="http://yjsxt.xidian.edu.cn/student/index.jsp";

        HttpPost httpPost=new HttpPost(loginUrl);

        List<NameValuePair> data=new ArrayList<NameValuePair>();

        data.add(new BasicNameValuePair("username","1601120078"));
        data.add(new BasicNameValuePair("password","208037"));
        data.add(new BasicNameValuePair("submit",""));
        data.add(new BasicNameValuePair("lt","LT-31860-Nr4DeRHVPUcMaNqcVrB5XxzLecI0ev1481623540759-wHmD-cas"));
        data.add(new BasicNameValuePair("execution","e1s1"));
        data.add(new BasicNameValuePair("_eventId","submit"));
        data.add(new BasicNameValuePair("rmShown","1"));


        httpPost.setEntity(new UrlEncodedFormEntity(data));

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
        printResponse(httpResponse);
        String newUrl=httpResponse.getFirstHeader("Location").getValue();

        setCookieStore(httpResponse);
        setContext();


        System.out.println(newUrl);

        HttpPost httpPost1=new HttpPost(newUrl);
        httpPost1.setEntity(new UrlEncodedFormEntity(data));
        CloseableHttpResponse httpResponse1 = httpClient.execute(httpPost1);
        printResponse(httpResponse1);

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
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:"
                    + responseString.replace("\r\n", ""));
        }
    }

    public static void setCookieStore(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getLastHeader("Set-Cookie")
                .getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
                setCookie.indexOf(";"));
        System.out.println("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie);
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

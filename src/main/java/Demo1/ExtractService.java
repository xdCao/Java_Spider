package Demo1;

import org.apache.http.util.TextUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/7/7.
 */
public class ExtractService {

    public static List<LinkTypeData> extract(Rule rule){

        validateRule(rule);

        List<LinkTypeData> datas=new ArrayList<LinkTypeData>();
        LinkTypeData data=null;
        try {
            String url=rule.getUrl();
            String[] params=rule.getParams();
            String[] values=rule.getValues();
            String resultTagName=rule.getResultTagName();
            int type=rule.getType();
            int requestType=rule.getRequestMethod();

            Connection connection= Jsoup.connect(url);

            if (params!=null){
                 for (int i=0;i<params.length;i++){
                     connection.data(params[i],values[i]);
                 }
            }

            Document document=null;
            switch (requestType){
                case Rule.GET:
                    document=connection.timeout(100000).ignoreContentType(true).get();
                    break;
                case Rule.POST:
                    document=connection.timeout(100000).ignoreContentType(true).post();
                    break;
            }

            Elements results=new Elements();
            switch (type){
                case Rule.CLASS:
                    results=document.getElementsByClass(resultTagName);
                    break;
                case Rule.ID:
                    Element result=document.getElementById(resultTagName);
                    results.add(result);
                    break;
                case Rule.SELECTION:
                    results=document.select(resultTagName);
                    break;
                default:
                    //resultTagName为空时默认取body
                    if (TextUtils.isEmpty(resultTagName)){
                        results=document.getElementsByTag("body");
                    }
            }

            for (Element result:results){
                Elements links=result.getElementsByTag("td");
                for (Element link:links){
                    String linkHref=link.attr("href");
                    String linkText=link.text();
                    data=new LinkTypeData();
                    data.setLinkHref(linkHref);
                    data.setLinkText(linkText);
                    datas.add(data);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return datas;

    }

    private static void validateRule(Rule rule) {

        String url=rule.getUrl();
        if (TextUtils.isEmpty(url)){
            throw new RuleException("url不能为空");
        }
        if (!url.startsWith("http://")){
            throw new RuleException("url格式不正确");
        }
        if (rule.getParams()!=null&&rule.getValues()!=null){
            if (rule.getParams().length!=rule.getValues().length){
                throw new RuleException("参数键值对个数不匹配符");
            }
        }

    }

    public static void main(String[] args){
        Rule rule=new Rule("http://jwc.xidian.edu.cn/info/1070/5113.htm",null,null,Rule.CLASS,"titlestyle49757", Demo1.Rule.GET);

        List<LinkTypeData> extracts= ExtractService.extract(rule);

        for (LinkTypeData data:extracts){
            System.out.println(data.getLinkText());

        }


        Rule rule2=new Rule("http://jwc.xidian.edu.cn/info/1070/5113.htm",null,null,Rule.CLASS,"contentstyle49757", Demo1.Rule.GET);

        List<LinkTypeData> extracts2= ExtractService.extract(rule2);

        for (LinkTypeData data:extracts2){
            System.out.println(data.getLinkText());

        }

    }

}

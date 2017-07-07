package WidthFirst;

import java.util.Set;

/**
 * Created by xdcao on 2017/7/5.
 */
public class MyCrawler {

    private void initCrawlerWithSeeds(String[] seeds){
        for (int i=0;i<seeds.length;i++){
            LinkQueue.addUnvisitedUrl(seeds[i]);
        }
    }

    public void crawing(String[] seeds){
        LinkFilter filter=new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www.sina.com"))
                    return true;
                else
                    return false;
            }
        };
        initCrawlerWithSeeds(seeds);
        while (!LinkQueue.unVisitedUrlIsEmpty()&&LinkQueue.getVisitedUrlNum()<=1000){
            String visitUrl=(String)LinkQueue.unVisitedUrlDequeue();
            if (visitUrl==null){
                continue;
            }
            DownloadFile downloadFile=new DownloadFile();
            downloadFile.downFile(visitUrl);
            LinkQueue.addVisitedUrl(visitUrl);
            Set<String> links=HtmlParserTool.extraLinks(visitUrl,filter);
            for (String link:links){
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }


    public static void main(String[] args){
        MyCrawler crawler=new MyCrawler();
        crawler.crawing(new String[]{"http://www.sina.com"});
    }

}

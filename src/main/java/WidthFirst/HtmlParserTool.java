package WidthFirst;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xdcao on 2017/7/4.
 */
public class HtmlParserTool {

    public static Set<String> extraLinks(String url, LinkFilter filter){
        Set<String> links=new HashSet<String>();
        try {
            Parser parser=new Parser(url);
            parser.setEncoding("utf-8");
            NodeFilter frameFilter=new NodeFilter() {
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")){
                        return true;
                    }else {
                        return false;
                    }
                }
            };
            OrFilter linkFilter=new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);
            NodeList list=parser.extractAllNodesThatMatch(linkFilter);
            for (int i=0;i<list.size();i++){
                Node tag=list.elementAt(i);
                if (tag instanceof LinkTag){
                    LinkTag link=(LinkTag)tag;
                    String linkUrl=link.getLink();
                    if (filter.accept(linkUrl))
                        links.add(linkUrl);
                }else {
                    String frame=tag.getText();
                    int start=frame.indexOf("src=");
                    frame=frame.substring(start);
                    int end=frame.indexOf(" ");
                    if (end==-1)
                        end=frame.indexOf(">");
                    String frameUrl=frame.substring(5,end-1);
                    if (filter.accept(frameUrl)){
                        links.add(frameUrl);
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }

}

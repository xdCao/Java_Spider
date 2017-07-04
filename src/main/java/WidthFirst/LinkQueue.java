package WidthFirst;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xdcao on 2017/7/4.
 */
public class LinkQueue {

    private static Set visitedUrl=new HashSet();

    private static Queue unvisitedUrl=new Queue();

    public static Queue getUnvisitedUrl(){
        return unvisitedUrl;
    }

    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }

    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }

    public static Object unVisitedUrlDequeue(){
        return unvisitedUrl.dequeue();
    }

    public static void addUnvisitedUrl(String url){
        if (url!=null&&!url.trim().equals("")&&!visitedUrl.contains(url)&&!unvisitedUrl.contains(url)){
            unvisitedUrl.enqueue(url);
        }
    }

    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    public static boolean unVisitedUrlIsEmpty(){
        return unvisitedUrl.isEmpty();
    }

}

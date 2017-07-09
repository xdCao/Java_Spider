package xdCao_spider_JiaoWuChu;

import java.util.Date;

/**
 * Created by xdcao on 2017/7/9.
 */
public class NoticeInformation {

    private String title;

    private String link;

    private String content;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title+", "+link+"\n"+content+"\n\n";
    }
}

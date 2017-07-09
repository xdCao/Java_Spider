package xdCao_spider_JiaoWuChu;

/**
 * Created by xdcao on 2017/7/9.
 */
public class NoticeInformation {

    private String title;

    private String link;

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

    @Override
    public String toString() {
        return title+", "+link;
    }
}

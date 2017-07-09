package Zhihu;

/**
 * Created by xdcao on 2017/7/7.
 */
public class ZhiHuUser {

    private String key;
    private String name;
    private String identity;
    private String location;
    private String profession;
    private int sex;
    private String school;
    private String major;
    private String recommend;
    private String picUrl;
    private int agree;
    private int thanks;
    private int ask;
    private int answer;
    private int article;
    private int collection;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getThanks() {
        return thanks;
    }

    public void setThanks(int thanks) {
        this.thanks = thanks;
    }

    public int getAsk() {
        return ask;
    }

    public void setAsk(int ask) {
        this.ask = ask;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "ZhihuUser [name=" + name + ", identity=" + identity + ", location=" + location + ", profession="
                + profession + ", sex=" + sex + ", school=" + school + ", major=" + major + ", recommend=" + recommend
                + ", picUrl=" + picUrl + ", agree=" + agree + ", thanks=" + thanks + ", ask=" + ask + ", answer="
                + answer + ", article=" + article + ", collection=" + collection + "]";
    }

}

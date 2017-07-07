package Demo1;

/**
 * Created by xdcao on 2017/7/7.
 * 定义查询过程中需要的信息，方便代码扩展和重用
 */
public class Rule {


    private String url;
    private String[] params;
    private String[] values;
    private int type=ID;
    private String resultTagName;
    private int requestMethod=GET;

    public static final int GET =0;
    public static final int POST=1;

    public static final int CLASS=0;
    public static final int ID=1;
    public static final int SELECTION=2;

    public Rule() {
    }

    public Rule(String url, String[] params, String[] values, int type, String resultTagName, int requestMethod) {
        this.url = url;
        this.params = params;
        this.values = values;
        this.type = type;
        this.resultTagName = resultTagName;
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResultTagName() {
        return resultTagName;
    }

    public void setResultTagName(String resultTagName) {
        this.resultTagName = resultTagName;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public static int getGET() {
        return GET;
    }

    public static int getPOST() {
        return POST;
    }

    public static int getCLASS() {
        return CLASS;
    }

    public static int getID() {
        return ID;
    }

    public static int getSELECTION() {
        return SELECTION;
    }
}

package start;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * Created by xdcao on 2017/7/4.
 */
public class RetrivePage {

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    public static boolean downloadPage(String path) {
        OutputStream outputStream=null;
        HttpGet httpGet=new HttpGet(path);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        int status=response.getStatusLine().getStatusCode();
        if (status== HttpStatus.SC_OK){
            String filename=path.substring(path.lastIndexOf('/')+1)+".html";
            try {
                outputStream=new FileOutputStream(filename);
                response.getEntity().writeTo(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        RetrivePage.downloadPage("http://gr.xidian.edu.cn/info/1073/5264.htm");
    }


}

package WidthFirst;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.*;

/**
 * Created by xdcao on 2017/7/4.
 */
public class DownloadFile {

    public String getFileNameByUrl(String url,String contentType){
        url=url.substring(7);
        if (contentType.contains("html")){
            url=url.replaceAll("[\\?/:*|<>\"]","_")+".html";
            return url;
        }else {
            return url.replaceAll("[\\?/:*|<>\"]","_")+"."+
                    contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }

    private void saveToLocal(byte[] data,String filaPath){
        try {
            DataOutputStream outputStream=new DataOutputStream(new FileOutputStream(new File(filaPath)));
            for (int i=0;i<data.length;i++){
                outputStream.write(data[i]);
            }
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downFile(String url){
        String filePath=null;
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);
        RequestConfig requestConfig=RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode=response.getStatusLine().getStatusCode();
            if (statusCode!= HttpStatus.SC_OK){
                System.err.println("failed, "+response.getStatusLine());
                filePath=null;
            }
            byte[] body= EntityUtils.toByteArray(response.getEntity());
            filePath="D://temp//"+getFileNameByUrl(url,response.getFirstHeader("Content-Type").getValue());
            saveToLocal(body,filePath);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpGet.releaseConnection();
        }
        return filePath;
    }




}

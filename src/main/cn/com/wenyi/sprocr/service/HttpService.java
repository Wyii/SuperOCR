package cn.com.wenyi.sprocr.service;

import cn.com.wenyi.sprocr.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wyiss on 2016/12/28.
 */
@Service
public class HttpService {

    public static String filePath = "img/";
    static private Logger logger = LoggerFactory.getLogger(HttpService.class);

    public String mkFile(InputStream inputStream) {
        OutputStream outputStream = null;
        String outputFileName =  filePath + String.valueOf(System.currentTimeMillis()) + ".png";
        try {


            outputStream =new FileOutputStream(new File(outputFileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (outputStream !=null) try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("mkfile:{}",outputFileName);
        return outputFileName;

    }

    public String request(String imageUrl) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection httpURLConnection = null;
        String outputFileName = "";
        String filePath = "img/";

        try {
            URL url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
            return mkFile(inputStream);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}

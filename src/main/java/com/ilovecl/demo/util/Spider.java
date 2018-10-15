package com.ilovecl.demo.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.ilovecl.demo._const.StudentConst.BASEURL;
import static com.ilovecl.demo._const.StudentConst.JSOUP_STUDENT_NAME;

/**
 * 获取学生姓名工具类
 */
public class Spider {

    private static final String VALIDATED = "xk/LoginToXkLdap";
    private HttpClient client;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 模拟登录获取学生姓名
     * @param sno 学号
     * @param psw 密码
     * @return 返回学生姓名
     * @throws IOException
     */
    public String getStudentName(String sno, String psw) throws IOException {
        client = HttpClients.createDefault();

        HttpResponse linkResponse = myGet(BASEURL);
        Header[] headers = linkResponse.getHeaders("Set-Cookie");

        String sessionId = headers[0].getValue();
        Header session = new BasicHeader("Cookie", sessionId);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("USERNAME", sno));
        params.add(new BasicNameValuePair("PASSWORD", psw));
        HttpResponse junkResponse = myPost(BASEURL+VALIDATED, params);

        Header headerLocation = junkResponse.getFirstHeader("Location");
        String location = headerLocation.getValue();

        HttpGet get = new HttpGet(location);
        get.setHeader(session);

        HttpResponse result = HttpClients.createDefault().execute(get);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(result.getEntity().getContent(), "utf-8"));

        StringBuilder sb = new StringBuilder();

        String line=null;
        while((line=reader.readLine())!=null){
            sb.append(line);
            sb.append("\n");
        }

        reader.close();

        Document doc = Jsoup.parse(sb.toString());
        Element element = doc.getElementById(JSOUP_STUDENT_NAME);

        return element.text();
    }


//    /**
//     * 验证学号密码是否正确
//     * @param sno 学号
//     * @param psw 密码
//     * @return
//     * @throws IOException
//     */
//    public boolean isIdentity(String sno, String psw) throws IOException {
//        logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++");
//        logger.info("isIdentity:"+sno);
//        logger.info("isIdentity:"+psw);
//        logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++");
//        client = HttpClients.createDefault();
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("USERNAME", sno));
//        params.add(new BasicNameValuePair("PASSWORD", psw));
//
//        HttpResponse response = myPost(BASEURL+VALIDATED, params);
//
//        if(response.getStatusLine().getStatusCode()==302){
//            return true;
//        }
//
//        return false;
//    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param params 表单数据
     * @return 返回响应
     * @throws IOException
     */
    public HttpResponse myPost(String url, List<NameValuePair> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if(params != null){
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        }

        return client.execute(httpPost);
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @return 返回响应
     * @throws IOException
     */
    public HttpResponse myGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);

        return client.execute(httpGet);
    }

}









package com.bootme.app.util.http;


import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);


    public static String getUrl(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new com.example.utils.HttpResponseHandler();

            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;
        } finally {
            httpclient.close();

        }

    }

    public static String postUrlWithJson(String url,String jsonString) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            StringEntity requestEntity = new StringEntity(
                    jsonString,
                    ContentType.APPLICATION_JSON);

            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(requestEntity);

            HttpResponse rawResponse = httpclient.execute(postMethod);

            System.out.println("Executing request " + postMethod.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new com.example.utils.HttpResponseHandler();

            String responseBody = httpclient.execute(postMethod, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }

    }


    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8084/jsonPost";
        String jsonString="channelId:\"aaaaaa\"";
        String ret = HttpClientUtil.postUrlWithJson(url,jsonString);
        System.out.println(ret);
    }


}

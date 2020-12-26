package com.yuyu.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CasClient {

    private static String getTicketGrantingTicket(final String server, final String username, final String password) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(server);

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username",username));
        list.add(new BasicNameValuePair("password",password));
        UrlEncodedFormEntity encodedFormEntity = null;
        try {
            encodedFormEntity = new UrlEncodedFormEntity(list,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(encodedFormEntity);

        //post.setRequestBody(new NameValuePair[] { new NameValuePair("username", username),new NameValuePair("password", password) });

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 201) {
                final Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(EntityUtils.toString(entity));
                if (matcher.matches())
                    return matcher.group(1);
                System.out.println("Successful ticket granting request, but no ticket found!");
            } else {
                System.out.println(EntityUtils.toString(entity));
                System.out.println("Invalid response code (" + response.getStatusLine().getStatusCode() + ") from CAS server!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getTicket(String server, String ticketGrantingTicket, String service) {
        if (ticketGrantingTicket == null) return null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(server + "/" + ticketGrantingTicket);
        CloseableHttpResponse response = null;

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("service",service));
        UrlEncodedFormEntity encodedFormEntity = null;
        try {
            encodedFormEntity = new UrlEncodedFormEntity(list,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(encodedFormEntity);

        try {
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(entity);
            } else {
                System.out.println(EntityUtils.toString(entity));
                System.out.println("Invalid response code (" + response.getStatusLine().getStatusCode() + ") from CAS server!");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static void ticketValidate(String serviceTicket, String service) {

        String serverValidate = "https://cas.treesims.com:10000/cas/serviceValidate";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = null;
        CloseableHttpResponse response = null;
        try {
            get = new HttpGet(serverValidate+"?"+"ticket="+serviceTicket+"&service="+ URLEncoder.encode(service, "UTF-8"));
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("得到数据" + EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String timsCasClient (String username, String password) {
        String server = "https://cas.treesims.com:10000/cas/v1/tickets";
        String service = "http://client.treesims.com:30000/cas/login";


        String ticketGrantingTicket = getTicketGrantingTicket(server, username, password);
        //ticketValidate(ticket, service);
        return getTicket(server, ticketGrantingTicket, service);
    }

}

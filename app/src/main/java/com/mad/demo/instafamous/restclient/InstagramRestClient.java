package com.mad.demo.instafamous.restclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class InstagramRestClient {

    private static final String BASE_URL = "https://api.instagram.com/v1/";
    //4c3a47ebfa184ad295ca246fd264b020
    private static final String CLIENT_ID = "<YOUR_CLIENT_ID_HERE>";
    private static final String CLIENT_ID_EXT = "?client_id=" + CLIENT_ID;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getPopularFeed(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String url = "/media/popular";
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl + CLIENT_ID_EXT;
    }
}

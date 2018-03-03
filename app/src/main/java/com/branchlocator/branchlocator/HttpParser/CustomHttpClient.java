package com.branchlocator.branchlocator.HttpParser;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by krupal.agravat on 2017-04-07.
 */

public class CustomHttpClient extends DefaultHttpClient {

    public CustomHttpClient() {
        super();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier(new CustomHostnameVerifier());
        Scheme scheme = (new Scheme("https", socketFactory, 443));
        getConnectionManager().getSchemeRegistry().register(scheme);
    }
}
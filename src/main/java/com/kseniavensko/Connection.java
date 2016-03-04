package com.kseniavensko;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Connection implements IConnection{
    private URL host;
    private String url;
    private int port;
    private MethodEnum method;
    private ProxyType proxyType = null;
    //private String proxyType = null;
    private String proxyUrl;
    private int proxyPort;
    //private URLConnection connection = null;

    public Connection(URL host) {
        this.host = host;
    }

    public Connection(URL host, String proxyType, URL proxyAddr) {
        this.host = host;
        this.proxyType = proxyType == "http" ? ProxyType.HTTP : ProxyType.SOCKS;
        // TODO: think about correct way of storing proxy
        this.proxyUrl = proxyAddr.getProtocol() + "://" + proxyAddr.getHost();
        this.proxyPort = proxyAddr.getPort();
    }

    public Connection(String url, int port, MethodEnum method) {
        this.url = url;
        this.port = port;
        this.method = method;
    }

    public Connection(String url, int port, MethodEnum method, ProxyType proxy, String proxyUrl, int proxyPort) {
        this.url = url;
        this.port = port;
        this.method = method;
        this.proxyType = proxy;
        this.proxyUrl = proxyUrl;
        this.proxyPort = proxyPort;
    }

//    public void openConnection() throws IOException {
//        URL url = new URL(method.value, this.url, port, "");
//        if (proxyType != null) {
//            Proxy proxy = new Proxy(proxyType.value, new InetSocketAddress(proxyUrl, proxyPort));
//            connection =  url.openConnection(proxy);
//        }
//        connection = url.openConnection();
//    }

    public Map<String, List<String>> getResponseHeaders() {
        URLConnection connection = null;
        try {
            connection = openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            return connection.getHeaderFields();
        } else return null;
    }

    private URLConnection openConnection() throws IOException {
        if (proxyType != null) {
            Proxy proxy = new Proxy(proxyType.value, new InetSocketAddress(proxyUrl, proxyPort));
            return host.openConnection(proxy);
        }
        return host.openConnection();
    }

    enum MethodEnum {
        HTTP("http"), HTTPS("https");
        private String value;

        MethodEnum(String value) {
            this.value = value;
        }
    }

    enum ProxyType {
        HTTP(Proxy.Type.HTTP), SOCKS(Proxy.Type.HTTP);
        private Proxy.Type value;

        ProxyType(Proxy.Type value) {
            this.value = value;
        }
    }

}



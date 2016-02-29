package com.kseniavensko;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Connection {
    private String url;
    private int port;
    private MethodEnum method;
    private ProxyType proxyType = null;
    private String proxyUrl;
    private int proxyPort;
    private URLConnection connection = null;

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

    public void openConnection() throws IOException {
        URL url = new URL(method.value, this.url, port, "");
        if (proxyType != null) {
            Proxy proxy = new Proxy(proxyType.value, new InetSocketAddress(proxyUrl, proxyPort));
            connection =  url.openConnection(proxy);
        }
        connection = url.openConnection();
    }

    public Map<String, List<String>> getResponseHeaders() {
        if (connection != null) {
            return connection.getHeaderFields();
        }
        else return null;
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



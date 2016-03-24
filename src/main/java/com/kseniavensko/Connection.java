package com.kseniavensko;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Connection implements IConnection {
    private URL host;
    private String redirectedHost;
    private ProxyType proxyType = null;
    private String proxyUrl;
    private int proxyPort;
    private Map<String, String> headers;
    private Logger logger = Logger.getInstance();

    public Connection(URL host, Map<String, String> headers) {
        this.host = host;
        this.headers = headers;
    }

    public Connection(URL host, String proxyType, String proxyAddr, Map<String, String> headers) {
        this.host = host;
        this.proxyType = proxyType == "http" ? ProxyType.HTTP : ProxyType.SOCKS;
        String[] proxy = proxyAddr.split(":", 2);
        this.proxyUrl = proxy[0];
        try {
            this.proxyPort = Integer.parseInt(proxy[1]);
        } catch (Exception e) {
            //TODO standard proxy port to config
            logger.log("Can not parse proxy port correctly. " + proxyAddr + " Trying to use standard port.");
            this.proxyPort = 80;
        }
        this.headers = headers;
    }

    public Map<String, List<String>> getResponseHeaders() throws IOException, RuntimeException {
        HttpURLConnection connection = null;
        URL url = host;

        while (true) {
            connection = (HttpURLConnection) openConnection(url);
            if (connection == null)
                break;
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setInstanceFollowRedirects(false);   // Make the logic below easier to detect redirections

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
//          URL base, next;

            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    redirectedHost = connection.getHeaderField("Location");
                    url = new URL(redirectedHost);
                    //base = new URL(url.toString());
                    //next = new URL(base, location);  // Deal with relative URLs
                    //url = next.toExternalForm();
                    continue;
            }

            break;
        }
        return connection == null ? null : connection.getHeaderFields();
    }

    private URLConnection openConnection(URL host) throws IOException {
        if (proxyType != null) {
            try {
                Proxy proxy = new Proxy(proxyType.value, new InetSocketAddress(proxyUrl, proxyPort));
                return host.openConnection(proxy);
            } catch (IllegalArgumentException e) {
                logger.log("Can not open proxy connection. " + proxyUrl + " Trying to connect without proxy.");
                host.openConnection();
            }
        }
        return host.openConnection();
    }

    public String getRedirectedHost() {
        return redirectedHost;
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



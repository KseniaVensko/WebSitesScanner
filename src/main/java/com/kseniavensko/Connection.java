package com.kseniavensko;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Connection implements IConnection {
    private URL host;
    private StringBuilder redirectedHost = new StringBuilder();
    private ProxyToScan proxy;
    private List<HeaderArgument> headers;
    private Logger logger = Logger.getInstance();

    public Connection(URL host, List<HeaderArgument> headers) {
        this.host = host;
        this.headers = headers;
    }

    public Connection(URL host, ProxyToScan proxy, List<HeaderArgument> headers) {
        this.host = host;
        this.proxy = proxy;
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
                for (HeaderArgument header : headers) {
                    connection.setRequestProperty(header.name, header.value);
                }
            }
//          URL base, next;
            int responseCode = connection.getResponseCode();
            if (String.valueOf(responseCode).matches("3[0-9]{2}")) {
                String location = connection.getHeaderField("Location");
                if (location != null) {
                    redirectedHost.append(location);
                    url = new URL(redirectedHost.toString());
                    redirectedHost.append(" with " + responseCode + " code, ");
                    //base = new URL(url.toString());
                    //next = new URL(base, location);  // Deal with relative URLs
                    //url = next.toExternalForm();
                    continue;
                }
            }
            //TODO: do I need to do the second request to the server?
            redirectedHost.append("code " + responseCode + " " + connection.getResponseMessage() + ",");
            break;
        }
        return connection == null ? null : connection.getHeaderFields();
    }

    private URLConnection openConnection(URL host) throws IOException {
        if (proxy != null) {
            try {
                Proxy.Type proxyType = proxy.getProto().equalsIgnoreCase("http") ? Proxy.Type.HTTP : Proxy.Type.SOCKS;
                Proxy p = new Proxy(proxyType, new InetSocketAddress(proxy.getAddr(), proxy.getPort()));
                return host.openConnection(p);
            } catch (IllegalArgumentException e) {
                logger.log("Can not open proxy connection. " + proxy.getAddr() + " Trying to connect without proxy.");
                return host.openConnection();
            }
        }
        return host.openConnection();
    }

    public String getRedirectedHost() {
        return redirectedHost.toString();
    }

}



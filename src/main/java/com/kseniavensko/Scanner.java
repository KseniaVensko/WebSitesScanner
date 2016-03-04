package com.kseniavensko;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Scanner implements IScanner {
//    public void Scan(String url, int port, Connection.MethodEnum method) {
//        Connection con = new Connection(url, port, method);
//        try {
//            con.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Map<String, List<String>> headerFields = con.getResponseHeaders();
//        Analyser analyser = new Analyser(headerFields);
//        analyser.printHeaders();
//    }

    public void scan(List<URL> hosts, String proxy_type, URL proxy_addr, Map<String, List<String>> headers, boolean resolveDns) {
        for (URL host : hosts) {
            Connection con = new Connection(host);
            con.getResponseHeaders();
        }
    }

    public ScanResult returnResults() {
        return null;
    }
}

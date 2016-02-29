package com.kseniavensko;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Scanner {
    public void Scan(String url, int port, Connection.MethodEnum method) {
        Connection con = new Connection(url, port, method);
        try {
            con.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> headerFields = con.getResponseHeaders();
        Analyser analyser = new Analyser(headerFields);
        analyser.printHeaders();
    }
}

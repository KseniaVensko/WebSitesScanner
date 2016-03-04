package com.kseniavensko.Fakes;

import com.kseniavensko.IConnection;
import com.kseniavensko.IScanner;
import com.kseniavensko.Result;
import com.kseniavensko.ScanResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FakeScanner implements IScanner {
    private List<Result> results = new ArrayList<Result>();

    public void scan(List<URL> hosts, String proxy_type, URL proxy_addr, Map<String, List<String>> headers, boolean resolveDns) {
        for (URL host : hosts) {
            IConnection con = new FakeConnection(host);
            Result result = new Result();
            result.setStringStatus("good" + host.toString());
            result.setInformationHeaders(con.getResponseHeaders());
            results.add(result);
        }
    }

    public ScanResult returnResults() {
        return new ScanResult(results);
    }
}

package com.kseniavensko.Fakes;

import com.kseniavensko.*;

import java.net.URL;
import java.util.*;

public class FakeScanner extends Observable implements IScanner {
    //private List<Observer> observers = new ArrayList<Observer>();
    private List<Result> results = new ArrayList<Result>();

    public void scan(List<URL> hosts, String proxy_type, String proxy_addr, Map<String, String> headers, boolean resolveDns) {
        int i = 0;
        int j = hosts.size();
        for (URL host : hosts) {
            //IConnection con = new FakeConnection(host);
            IConnection con = new Connection(host, headers);
            Result result = new Result();
            result.setHost(host);
            result.setStringStatus("good");
           // result.setInformationHeaders(con.getResponseHeaders());
            results.add(result);
            i++;
            setChanged();
            notifyObservers("scanned: " + i + "/" + j);
        }
    }

    public ScanResult returnResults() {
        return new ScanResult(results);
    }
}

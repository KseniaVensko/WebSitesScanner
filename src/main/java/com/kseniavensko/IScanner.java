package com.kseniavensko;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface IScanner {
    void scan(List<URL> hosts, ProxyToScan proxy, List<HeaderArgument> headers, boolean resolveDns);
    ScanResult returnResults();
}

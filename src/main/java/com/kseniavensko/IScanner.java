package com.kseniavensko;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface IScanner {
    void scan(List<URL> hosts, String proxy_type, URL proxy_addr, Map<String, List<String>> headers, boolean resolveDns);
    ScanResult returnResults();
}

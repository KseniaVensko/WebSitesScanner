package com.kseniavensko;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface IScanner {
    void scan(List<URL> hosts, String proxy_type, String proxy_addr, Map<String, String> headers, boolean resolveDns);
    ScanResult returnResults();
}

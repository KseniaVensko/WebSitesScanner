package com.kseniavensko;

import com.kseniavensko.Args.HeaderArgument;
import com.kseniavensko.Args.ProxyToScan;

import java.net.URL;
import java.util.List;

public interface IScanner {
    void scan(List<URL> hosts, ProxyToScan proxy, List<HeaderArgument> headers, boolean resolveDns);
    ScanResult returnResults();
}

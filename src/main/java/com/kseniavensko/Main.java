package com.kseniavensko;

import com.beust.jcommander.JCommander;
import com.kseniavensko.Fakes.FakeScanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//curl -I site  - retreive headers (to compare with my implementation)

public class Main {

    public static void main(String[] args) {
        final Arguments jct = new Arguments();
        JCommander jcm = new JCommander(jct, args);
        if (jct.help) {
            jcm.usage();
            return;
        }

        List<URL> hosts = new ArrayList<URL>();

        if (jct.file != null) {
            UrlsReaderFromFile reader = new UrlsReaderFromFile();
            hosts = reader.read(jct.file);
        }
        // I`ve decided to allow use --host and --input_file together
        if (jct.hosts != null) {
            hosts.addAll(jct.hosts);
        }

        final List<URL> finalHosts = hosts;

        if (finalHosts.isEmpty()) {
            System.out.println("--host or --input_file is required\n");
            return;
        }

        for (URL host : finalHosts) {
            if (host == null)
                return;
        }

        if (jct.headers != null) {
            for (Map.Entry<String, String> val : jct.headers.entrySet()) {
                if (val.getValue() == null) {
                    System.out.println("header value is not correct for header : " + val.getKey());
                    return;
                }
            }
        }

        final ProgressObserver ob = new ProgressObserver();
        Scanner scanner = new Scanner();
        scanner.addObserver(ob);
        scanner.scan(finalHosts, jct.proxy_type, jct.proxy_addr, jct.headers, false);
        ScanResult result = scanner.returnResults();
        result.toConsole();
        if (jct.json_file != null) {
            result.toJsonFile(jct.json_file);
        }
    }
}

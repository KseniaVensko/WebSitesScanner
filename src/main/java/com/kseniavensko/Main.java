package com.kseniavensko;

import com.beust.jcommander.JCommander;
import com.kseniavensko.Fakes.FakeScanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.cli.*;

//String[] argv = { "--host", "http://127.0.0.1:8000", "https://ololo:443", "--file", "file.txt" };
//WebSiteScanner --host http://127.0.0.1:80 --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080
//WebSiteScanner -u 127.0.0.1 -p 8000 -m http
//WebSiteScanner -u 127.0.0.1 -p 8000 -m http --headers "one : one_value" "two : two_value"
//WebSiteScanner --input sites.txt --output result.json
//WebSiteScanner -i sites.txt -o result.json --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080

public class Main {

    public static void main(String[] args) {
        final Arguments jct = new Arguments();
        JCommander jcm = new JCommander(jct, args);
//       jcm.usage();

        List<URL> hosts = new ArrayList<URL>();

        if (jct.file != null) {
            UrlsReaderFromFile reader = new UrlsReaderFromFile();
            hosts = reader.read(jct.file);
        }
        // I`ve decided to allow use --host and --file together
        if (jct.hosts != null) {
            hosts.addAll(jct.hosts);
        }

        final List<URL> finalHosts = hosts;

        final ProgressObserver ob = new ProgressObserver();
//        new Thread(new Runnable() {
//            public void run() {
        FakeScanner scanner = new FakeScanner();
        scanner.addObserver(ob);
        scanner.scan(finalHosts, jct.proxy_type, jct.proxy_addr, jct.headers, false);
        ScanResult result = scanner.returnResults();
        result.toConsole();
        if (jct.json_file != null) {
            result.toJsonFile(jct.json_file);
        }
//            }
//        }).start();
    }
}

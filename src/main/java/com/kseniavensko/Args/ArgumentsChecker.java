package com.kseniavensko.Args;

import com.kseniavensko.Logger;
import com.kseniavensko.UrlsReaderFromFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsChecker {
    public CheckedArguments check(Arguments jct) {
        CheckedArguments arg = new CheckedArguments();
        arg.correct = true;
        Logger logger = Logger.getInstance();
        if (jct.help) {
            System.out.println(help);
            arg.correct = false;
        }

        List<URL> hosts = new ArrayList<>();

        if (jct.file != null) {
            UrlsReaderFromFile reader = new UrlsReaderFromFile();
            try {
                hosts = reader.read(jct.file);
            } catch (FileNotFoundException e) {
                logger.log("Input file " + jct.file + " was not found.");
            } catch (IOException e) {
                logger.log("Can not read from  " + jct.file);
            }
        }
        // I`ve decided to allow use --host and --input_file together
        if (jct.hosts != null) {
            try {
                hosts.addAll(jct.hosts);
            } catch (Exception e) {
                logger.log("Can not properly add input hosts to collection to check.");
            }
        }

        final List<URL> finalHosts = hosts;

        if (finalHosts.isEmpty()) {
            System.out.println("--host or --input_file is required\n");
            arg.correct = false;
        }

        for (URL host : finalHosts) {
            if (host == null) {
                logger.log("Host is null.");
                arg.correct = false;
            }
        }

        arg.hosts = finalHosts;

        if (jct.headers != null) {
            for (HeaderArgument header : jct.headers) {
                if (header.value == null) {
                    System.out.println("header value is not correct for header : " + header.name);
                    arg.correct = false;
                }
            }
        }

        arg.headers = jct.headers;
        if (jct.proxy != null && !jct.proxy.isCorrect()) {
            System.out.println("Proxy value is not correct. See Readme for proper usage.");
            arg.correct = false;
        }

        arg.proxy = jct.proxy;
        return arg;
    }
    String help = "Usage:\n" +
            "java -jar WebSitesScanner [--host url] [--input_file path] [--output_file path] [--header header_object] [--proxy proxy_url]\n" +
            "\n" +
            "Options:\n" +
            "        --host url\n" +
            "            Specify the URL you want to scan; URL should be in format [scheme://]host[:port] where scheme part and port are not required;\n" +
            "            Scheme is either http or https. The default value for scheme is http and port is 80.\n" +
            "            You can use this option multiple times, example:\n" +
            "\n" +
            "            java -jar WebSitesScanner --host http://example.com --host example2.com\n" +
            "\n" +
            "        --input_file path\n" +
            "            Specify the absolute path to the text file which contains URLs in format [scheme://]host[:port] separated by line breaks, example:\n" +
            "            example.com\n" +
            "            https://example2.com\n" +
            "            ...\n" +
            "\n" +
            "        Note that either at least one key --host or key --input_file is required. If you specify both then both will be processed.\n" +
            "\n" +
            "        --output_file path\n" +
            "            Specify the absolute path to file for program output in json format, not required.\n" +
            "\n" +
            "        --headers header_object\n" +
            "            Specify the http request header with value you want to send additionally to standard headers during scanning, not required.\n" +
            "            It must be in format name:value. You can use this option multiple times, example:\n" +
            "            java -jar WebSitesScanner --input_file hosts.txt --header User-Agent:\"Mozilla/5.0\" --header Referer:/news/2007/08/23/\n" +
            "\n" +
            "            Note that before and after colon must not be additional spaces.\n" +
            "\n" +
            "        --verbose\n" +
            "            Force program to display warnings and errors to the console during scanning\n" +
            "\n" +
            "        --help\n" +
            "            Prints help message and quits\n" +
            "\n" +
            "        --proxy proxy_url\n" +
            "            Specify the address (in format proto://ip:port) of proxy, not required.\n" +
            "            Proto should be either http or socks, ex:\n" +
            "            --proxy socks://10.10.10.1:8888\n" +
            "            --proxy http://10.0.0.1:8080";
}

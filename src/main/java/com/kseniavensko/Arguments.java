package com.kseniavensko;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.kseniavensko.Converters.HeadersConverter;
import com.kseniavensko.Converters.ProxyConverter;
import com.kseniavensko.Converters.UrlConverter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Arguments {
    @Parameter(names = "--host", description = "Url you want to scan\n" +
            "\t\t\tfor more info see Readme", converter = UrlConverter.class, variableArity = true)
    public List<URL> hosts = new ArrayList<URL>();

    @Parameter(names = "--proxy", description = "IP addr of proxy in format proto://ip:port", converter = ProxyConverter.class)
    ProxyToScan proxy;

    @Parameter(names = "--headers", description = "Array of headers separated by commas\n\t\t\tfor more info see Readme", converter = HeadersConverter.class)
    public Map<String, String> headers;

    @Parameter(names = "--input_file", description = "The text file with urls\n" +
            "\t\t\tfor more info see Readme", converter = FileConverter.class)
    File file;

    @Parameter(names = "--output_file", description = "Output file for json format\n" +
            "\t\t\tfor more info see Readme")
    public String json_file;

    @Parameter(names = "--help", help = true)
    public boolean help = false;

    @Parameter(names = "--log", description = "If you want to see log messages")
    public boolean log = false;
}


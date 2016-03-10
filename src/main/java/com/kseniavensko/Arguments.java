package com.kseniavensko;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Arguments {
    @Parameter(names = "--host", description = "The host", converter = UrlConverter.class, variableArity = true)
    public List<URL> hosts = new ArrayList<URL>();

    @Parameter(names = "--proxy_type", description = "Type of proxy(http or socks)")
    public String proxy_type;

    @Parameter(names = "--proxy_addr", description = "IP addr of proxy", converter = UrlConverter.class)
    URL proxy_addr;

    @Parameter(names = "--headers", description = "http headers")
    public Map<String, List<String>> headers;

    @Parameter(names = "--input_file", converter = FileConverter.class)
    File file;

    @Parameter(names = "--output_file", description = "Output file for json format string")
    public String json_file;
}


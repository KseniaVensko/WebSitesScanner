package com.kseniavensko;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IConnection {
    public Map<String, List<String>> getResponseHeaders() throws IOException, RuntimeException;
    public String getRedirectedHost();
}

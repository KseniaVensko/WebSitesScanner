package com.kseniavensko;

import java.util.List;
import java.util.Map;

public interface IConnection {
    public Map<String, List<String>> getResponseHeaders();
}

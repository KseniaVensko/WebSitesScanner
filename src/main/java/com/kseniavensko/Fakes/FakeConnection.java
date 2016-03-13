package com.kseniavensko.Fakes;

import com.kseniavensko.IConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeConnection implements IConnection {

    private final URL host;
    private Map<String, String> headers;

    public FakeConnection(URL host, Map<String, String> headers) {
        this.host = host;
        this.headers = headers;
    }

    public Map<String, List<String>> getResponseHeaders() {
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return new HashMap<String, List<String>>() {
            {
                put("Server", new ArrayList<String>() {
                    {
                        add("First.One");
                        add("First.Two");
                        add("First.Three");
                    }
                });
                put("x-powered-by", new ArrayList<String>() {
                    {
                        add("Second.One");
                        add("Second.Two");
                        add("Second.Three");
                    }
                });
                put("x-content-type-options", new ArrayList<String>() {
                    {
                        add("nosniff");
                    }
                });
            }
        };
    }
}

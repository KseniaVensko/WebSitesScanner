package com.kseniavensko.Fakes;

import com.kseniavensko.IConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeConnection implements IConnection {

    private final URL host;

    public FakeConnection(URL host) {
        this.host = host;
    }

    public Map<String, List<String>> getResponseHeaders() {
        try {
            Thread.sleep(5000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return new HashMap<String, List<String>>() {
            {
                put(host.getPath() + "First", new ArrayList<String>() {
                    {
                        add("First.One");
                        add("First.Two");
                        add("First.Three");
                    }
                });
                put(host.getPath() + "Second", new ArrayList<String>() {
                    {
                        add("Second.One");
                        add("Second.Two");
                        add("Second.Three");
                    }
                });
            }
        };
    }
}

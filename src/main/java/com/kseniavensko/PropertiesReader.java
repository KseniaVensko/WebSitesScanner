package com.kseniavensko;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class PropertiesReader {
    public TreeSet<String> readCookies() throws IOException {
        TreeSet<String> cookies = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Properties props = new Properties();
        props.load(new FileInputStream(new File("config.properties")));
        String[] googleCookies = props.getProperty("googleCookies").split(";");
        String[] yandexCookies = props.getProperty("yandexCookies").split(";");
        try {
            Collections.addAll(cookies, googleCookies);
            Collections.addAll(cookies, yandexCookies);
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }

        return cookies;
    }
}

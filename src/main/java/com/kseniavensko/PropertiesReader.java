package com.kseniavensko;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.TreeSet;

/**
 * reads properties from file config.properties
 */
public class PropertiesReader {
    public TreeSet<String> readCookies() throws Exception {
        TreeSet<String> cookies = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Properties props = new Properties();
        props.load(new FileInputStream(new File("config.properties")));
        String[] googleCookies = props.getProperty("googleCookies").split(";");
        String[] yandexCookies = props.getProperty("yandexCookies").split(";");
        Collections.addAll(cookies, googleCookies);
        Collections.addAll(cookies, yandexCookies);
        return cookies;
    }
}

package com.kseniavensko.Converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlConverter implements IStringConverter<URL> {
    public URL convert(String s) {
        try {
            Pattern p = Pattern.compile("^(http:\\/\\/|https:\\/\\/)(.*)");
            Matcher m = p.matcher(s);
            StringBuilder u = new StringBuilder();
            if (!m.matches()) {
                u.append("http://");
            }
            u.append(s);
            URL url = new URL(u.toString());
            return url;
        } catch (MalformedURLException e) {
            System.out.println("URL is not correct: " + e.getMessage());
        }
        return null;
    }
}

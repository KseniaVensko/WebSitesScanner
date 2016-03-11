package com.kseniavensko.Converters;

import com.beust.jcommander.IStringConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlConverter implements IStringConverter<URL> {
    public URL convert(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //TODO: is this correct?
        return null;
    }
}

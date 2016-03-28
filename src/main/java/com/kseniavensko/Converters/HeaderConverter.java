package com.kseniavensko.Converters;

import com.beust.jcommander.IStringConverter;
import com.kseniavensko.HeaderArgument;

public class HeaderConverter implements IStringConverter<HeaderArgument> {
    @Override
    public HeaderArgument convert(String s) {
        HeaderArgument header = new HeaderArgument();
        String[] parts = s.split(":", 2);
        header.name = parts[0];
        if (parts.length < 2) {
            header.value = null;
        }
        else {
            header.value = parts[1];
        }
        return header;
    }
}

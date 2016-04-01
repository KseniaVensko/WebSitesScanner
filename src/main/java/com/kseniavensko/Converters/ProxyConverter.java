package com.kseniavensko.Converters;

import com.beust.jcommander.IStringConverter;
import com.kseniavensko.Logger;
import com.kseniavensko.ProxyToScan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * parse proxy console argument
 * socks://10.10.10.10:8080
 * http://10.0.0.1:8888
 * proto can be socks or httponly
 * returns ProxyToScan
 */
public class ProxyConverter implements IStringConverter<ProxyToScan> {
    Pattern p = Pattern.compile("(socks|http)\\:\\/\\/((?:[0-9]{1,3}\\.){3}[0-9]{1,3})\\:([0-9]+)");
    Logger logger = Logger.getInstance();

    @Override
    public ProxyToScan convert(String s) {
        ProxyToScan proxy = new ProxyToScan();
        Matcher m = p.matcher(s);

        if (m.matches()) {
            try {
                proxy.setProto(m.group(1));
                proxy.setAddr(m.group(2));
                int p;
                p = Integer.parseInt(m.group(3));
                proxy.setPort(p);
                proxy.setCorrect(true);
            }
            catch (Exception e) {
                proxy.setCorrect(false);
            }
        }
        else {
            proxy.setCorrect(false);
        }
        return proxy;
    }
}

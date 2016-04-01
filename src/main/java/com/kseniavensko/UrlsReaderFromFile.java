package com.kseniavensko;

import com.kseniavensko.Converters.UrlConverter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * reads from file(absolute path)
 * file in such format:
 * url1
 * url2
 * ...
 * urln
 * where url in format [scheme://]host[:port] where scheme part and port are not required
 */
public class UrlsReaderFromFile {
    public List<URL> read(File file) throws FileNotFoundException, IOException{
        List<URL> hosts = new ArrayList<URL>();

        FileReader r = new FileReader(file);
        UrlConverter c = new UrlConverter();
        try {
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                hosts.add(c.convert(line));
            }

        } catch (IOException e) {
            throw e;
        }
        finally {
            r.close();
        }
        return hosts;
    }
}

package com.kseniavensko;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlsReaderFromFile {
    public List<URL> read(File file) throws FileNotFoundException, IOException{
        List<URL> hosts = new ArrayList<URL>();

        FileReader r = new FileReader(file);
        try {
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                hosts.add(new URL(line));
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

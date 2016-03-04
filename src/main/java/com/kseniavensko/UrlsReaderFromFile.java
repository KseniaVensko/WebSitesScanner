package com.kseniavensko;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlsReaderFromFile {
    public List<URL> read(File file) {
        List<URL> hosts = new ArrayList<URL>();
        try {
            FileReader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                // TODO: it's not beautiful maybe
                hosts.add(new URL(line));
            }
            r.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Smth wrong with file");
            e.printStackTrace();
        }
        return hosts;
    }
}

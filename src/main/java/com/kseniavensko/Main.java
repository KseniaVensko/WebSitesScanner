package com.kseniavensko;

import com.beust.jcommander.JCommander;
import com.kseniavensko.Fakes.FakeScanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.cli.*;

//String[] argv = { "--host", "http://127.0.0.1:8000", "https://ololo:443", "--file", "file.txt" };
//WebSiteScanner --url 127.0.0.1 --port 8000 --method http --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080
//WebSiteScanner -u 127.0.0.1 -p 8000 -m http
//WebSiteScanner -u 127.0.0.1 -p 8000 -m http --headers "one : one_value" "two : two_value"
//WebSiteScanner --input sites.txt --output result.json
//WebSiteScanner -i sites.txt -o result.json --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080

public class Main {

    public static void main(String[] args) {

        //Parser = new Parser(bool file)
        //              CommandLineParser parser = new CommandLineParser()
        //              FileParser parser = new FileParser()
        //arguments = parser.Parse(args)
        //              arguments.getHeaders

        Arguments jct = new Arguments();
        JCommander jcm = new JCommander(jct, args);
       /* jcm.usage();
        */
        List<URL> hosts = new ArrayList<URL>();

        if (jct.file != null) {
            UrlsReaderFromFile reader = new UrlsReaderFromFile();
            hosts = reader.read(jct.file);
        }
        // I`ve decided to allow use --host and --file together
        if (jct.hosts != null) {
            hosts.addAll(jct.hosts);
        }
        IScanner scanner = new FakeScanner();
        scanner.scan(hosts, jct.proxy_type, jct.proxy_addr, jct.headers, false);
        ScanResult result = scanner.returnResults();
        
        System.out.println(result.asText());
        //Scanner scanner = new Scanner(hosts, jct.proxy_type, jct.proxy_addr, jct.headers, resolveDns);
        //Scanner.Scan(); -> new thread() -> observer --
        //show scan progress in this thread          <__|
        //Statistic statistic = Scanner.getStatisticResult();
        //statistic.toConsole()
        //statistic.toJsonFile()
        //tryCli();
        //createOptions(args);


//        Scanner scanner = new Scanner();
//        scanner.Scan("127.0.0.1", 8000, Connection.MethodEnum.HTTP);
//        scanner.Scan("www.google.com", 80, Connection.MethodEnum.HTTP);
//        scanner.Scan("www.google.com", 443, Connection.MethodEnum.HTTPS);
//        private static Map<String, List<String>> readHostsFromFile(File file) {
//
//        }

    }

    /*public static void createOptions(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();


        Option option = new Option("u", "url", true, "address");
        option.setArgs(1);
        option.setArgName("url");
        options.addOption( option );


        option = new Option("p", "port", true, "port");
        option.setArgs(1);
        option.setArgName("PORT");
        options.addOption( option );

        option = new Option("r", "proxy_url", true, "proxy addr");
        option.setArgs(1);
        option.setArgName("url");
        options.addOption( option );

        option = new Option("e", "headers", true, "http headers, not required");
        option.setValueSeparator(',');
        option.setArgs(Option.UNLIMITED_VALUES);
        option.setArgName("header1:value1,header2:value2...");
        option.setRequired(false);
        options.addOption( option );

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "WebSitesScanner", options );

        // String[] args = new String[]{ "--url=127.7.7.7", "--foo=lala" };

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            // validate that block-size has been set
            if( line.hasOption( "headers" ) ) {
                // print the value of block-size
                for (String i:line.getOptionValues("headers")) {
                    System.out.println(i);
                }

                //line.getOptionValues("headers");
            }
            if( line.hasOption( "url" ) ) {
                // print the value of block-size
                System.out.println( line.getOptionValue( "url" ) );
            }
            if( line.hasOption( "foo" ) ) {
                // print the value of block-size
                System.out.println( line.getOptionValue( "foo" ) );
            }
        }
        catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
    }*/
}

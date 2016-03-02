package com.kseniavensko;

import org.apache.commons.cli.*;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //WebSiteScanner --url 127.0.0.1 --port 8000 --method http --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080
        //WebSiteScanner -u 127.0.0.1 -p 8000 -m http
        //WebSiteScanner -u 127.0.0.1 -p 8000 -m http --headers "one : one_value" "two : two_value"
        //WebSiteScanner --input sites.txt --output result.json
        //WebSiteScanner -i sites.txt -o result.json --proxy_type http --proxy_url 127.0.0.2 --proxy_port 8080

        //Parser = new Parser(bool file)
        //              CommandLineParser parser = new CommandLineParser()
        //              FileParser parser = new FileParser()
        //arguments = parser.Parse(args)
        //              arguments.getHeaders
        //Scanner scanner = new Scanner(urls, proxy, headers, resolveDns);
        //Scanner.Scan(); -> new thread() -> observer --
        //show scan progress in this thread          <__|
        //Statistic statistic = Scanner.getStatisticResult();
        //statistic.toConsole()
        //statistic.toJsonFile()
        createOptions();
//        Scanner scanner = new Scanner();
//        scanner.Scan("127.0.0.1", 8000, Connection.MethodEnum.HTTP);
//        scanner.Scan("www.google.com", 80, Connection.MethodEnum.HTTP);
//        scanner.Scan("www.google.com", 443, Connection.MethodEnum.HTTPS);
    }

    public static void createOptions() {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption( "a", "all", false, "do not hide entries starting with ." );
        options.addOption( OptionBuilder.withLongOpt( "block-size" )
                .withDescription( "use SIZE-byte blocks" )
                .hasArg()
                .withArgName("SIZE")
                .create() );
        String[] args = new String[]{ "--block-size=10" };

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            // validate that block-size has been set
            if( line.hasOption( "block-size" ) ) {
                // print the value of block-size
                System.out.println( line.getOptionValue( "block-size" ) );
            }
        }
        catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
    }
}

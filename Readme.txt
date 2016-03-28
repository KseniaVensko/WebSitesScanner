Program sends request to the servers and analyse response headers and prints results and recommendations. To use it you need Java8 or higher.

Usage:
java -jar WebSitesScanner [--host url] [--input_file path] [--output_file path] [--headers headers_with_values] [--proxy proxy_url]

Options:
        --host url
            Specify the URL you want to scan; URL can be like [scheme://]host[:port] where scheme part and port not required;
            Scheme can be http or https. The default value for scheme is http:// and port is 80.
            You can use this option multiply times, ex:
            WebSitesScanner --host http://example.com --host example2.com

        --input_file path
            Specify the absolute path to the text file which contains URLs like [scheme://]host[:port] separated by line breaks, ex:
            example.com
            https://example2.com
            ...

        Note that either at least one key --host or key --input_file is required. If you specify both then both will be processed.

        --output_file path
            Specify the absolute path to file for program output in json format, not required.

        --headers headers_with_values
            Specify the hash of headers with values you want to send additionally to standard headers during scanning, not required.
            It looks like [header_object]&&[header_object]&&... Note that string must not contain spaces before and after &&.
            where [header_object] consist name and value of header separeted by colon, ex:
            WebSitesScanner --input_file hosts.txt --headers "User-Agent":"Mozilla/5.0"&&Referer:"http://www.example.com/"

        --log
            Force program to display warnings and errors to console during scanning

        --help
            Prints help message and quits

        --proxy proxy_url
            Specify the address (like scheme://ip:port) of proxy, not required.
            Scheme can be http or socks, ex:
            --proxy socks://10.10.10.1:8888
            --proxy http://10.0.0.1:8080

Examples:
* java -jar WebSitesScanner.jar --input_file input.txt --headers "User-Agent":"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0" --proxy socks://127.0.0.0:9150
        program will read URLs from input.txt, send requests to that URLs with specified User-Agent header through specified socks proxy and prints results to the console.

* java -jar WebSitesScanner.jar --host facebook.com --input_file input2.txt --output_file out.json --log --proxy http://127.0.0.1:8888
        program will read URLs from input.txt, send requests to that URLs and specified URL facebook.com through http proxy,
        prints results to the console and to the out.json file, prints errors and warnings during scanning to the console.

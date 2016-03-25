java -jar WebSitesScanner [--host url] [--input_file absolute_file_path] [--output_file absolute_file_path.json] [--headers h1:v1, h2:v2, ...]
        --host is the url you want to scan
            you can use it any times you want, ex:
            WebSitesScanner --host http://example.com --host http://example2.com

        --input_file is the text file with urls in such format:
            http://example.com
            https://example2.com
            ...
        Note that either at least one key --host or key --input_file is required

        --output_file is the file to write output in json format, not required

        --headers is array of headers you want to send additionally to standard headers while scanning the hosts, ex:
            WebSitesScanner --input_file hosts.txt --headers "User-Agent":"Mozilla/5.0"&&Referer:"http://www.example.com/"
        Note that string must not contain spaces after &&, i.e you must not write this:
            h1:v1 && h2:v2 && h3:v3
        only:
            h1:"v1"&&h2:"v2"&&h3:"v3"

        --log is the flag that force program to display errors to console during scanning

        --help prints help message and quits

        --proxy is the address(proto://ip:port) of proxy
            where proto can be http or socks, ex:
            --proxy socks://10.10.10.1:8888
            --proxy http://10.0.0.1:8080
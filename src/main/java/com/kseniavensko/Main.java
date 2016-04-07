package com.kseniavensko;

import com.beust.jcommander.JCommander;
import com.kseniavensko.Args.Arguments;
import com.kseniavensko.Args.ArgumentsChecker;
import com.kseniavensko.Args.CheckedArguments;

//curl -I site  - retreive headers (to compare with my implementation)

public class Main {

    public static void main(String[] args) {
        // System.setProperty("sun.net.spi.nameservice.nameservers", "8.8.8.8");
        // System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");

        Logger logger = Logger.getInstance();
        final Arguments jct = new Arguments();

        JCommander jcm = new JCommander(jct, args);
        ArgumentsChecker checker = new ArgumentsChecker();
        CheckedArguments arg = checker.check(jct);
        if (!arg.correct) {
            return;
        }

        final ProgressObserver ob = new ProgressObserver();
        Scanner scanner = new Scanner();
        scanner.addObserver(ob);
        scanner.scan(arg.hosts, arg.proxy, arg.headers, false);
        ScanResult result = scanner.returnResults();
        result.toConsole();
        if (jct.json_file != null) {
            result.toJsonFile(jct.json_file);
        }
        if (jct.verbose) {
            logger.writeToConsole();
        }
    }
}

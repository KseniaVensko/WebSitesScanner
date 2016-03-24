package com.kseniavensko;

public class Logger {
    private static Logger ourInstance = new Logger();
    private StringBuilder stringLog;

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {
        stringLog = new StringBuilder();
    }

    public void log(String toLog) {
        stringLog.append(toLog + "\n");
    }

    public void writeToConsole() {
        System.out.println("Errors during scanning:");
        System.out.println(stringLog.toString());
    }
}

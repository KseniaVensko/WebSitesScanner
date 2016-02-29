package com.kseniavensko;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner();
        scanner.Scan("127.0.0.1", 8000, Connection.MethodEnum.HTTP);
        scanner.Scan("www.google.com", 80, Connection.MethodEnum.HTTP);
        scanner.Scan("www.google.com", 443, Connection.MethodEnum.HTTPS);
    }
}

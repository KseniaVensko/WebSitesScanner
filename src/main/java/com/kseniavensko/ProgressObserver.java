package com.kseniavensko;

import java.util.Observable;
import java.util.Observer;

/**
 * prints string which is sent by observable object(erases the previous one and prints on its place the new string)
 */
public class ProgressObserver implements Observer {
    public void update(Observable o, Object arg) {
        final int width = 100;
        System.out.print("\r");
        for (int i = 0; i < width; ++i) {
            System.out.print(" ");
        }
        System.out.print("\r");
        System.out.print(arg.toString());
    }
}

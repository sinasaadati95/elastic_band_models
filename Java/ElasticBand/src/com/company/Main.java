package com.company;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("Sina Saadati | sina.saadati@aut.ac.ir ");
        JApplet ja = new Screen();
        jf.getContentPane().add(ja);
        jf.pack();
        jf.setSize(1100, 750 );
        jf.setVisible(true);
    }
}

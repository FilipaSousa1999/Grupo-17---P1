package server.src.main.java;

import java.net.Socket;

public class Main {

    public static void main ( String[] args ) {
        ServerThread server = new ServerThread ( 8888);
        server.start();
    }
}
package server.src.main.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main ( String[] args ) throws IOException {
        ServerThread server = new ServerThread ( 8888);
        server.start();

    }

}
package server.src.main.java;

import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main ( String[] args ) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
            try {
                ServerSocket server = new ServerSocket(8888);

                while (true) {
                    System.out.println("Aguardar conexão...");
                    Socket con = server.accept();
                    System.out.println("Cliente conetado...");
                    ServerThread serverThread = new ServerThread(con, threadList);
                    threadList.add(serverThread);
                    serverThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
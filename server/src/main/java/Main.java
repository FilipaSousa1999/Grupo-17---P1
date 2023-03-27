package main.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    private static int max = 2;
    private static Semaphore semaphore;

    public static void main ( String[] args ) {

        ArrayList<ServerThread> threadList = new ArrayList<>();
        semaphore = new Semaphore(max);

            try {
                ServerSocket server = new ServerSocket(8888);

                while (true) {
                    System.out.println("Aguardar conexão...");
                        semaphore.acquire ( );
                        Socket con = server.accept();
                        System.out.println("Cliente conetado...");
                        ServerThread serverThread = new ServerThread(con, threadList, semaphore);
                        threadList.add(serverThread);
                        serverThread.start();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}


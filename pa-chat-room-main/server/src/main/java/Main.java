package server.src.main.java;

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


        /*
        File filtro = new File("C:\\Users\\Vasily Frolov\\Documents\\GitHub\\Grupo-17---P1\\pa-chat-room-main\\server\\filtro.txt");
        FileReader fr = null;
        try {
            fr = new FileReader(filtro);
            String[] words = null; //array
            BufferedReader bfr_filtro = new BufferedReader(fr); //indicar pathname
            int count =0;
            String current_line;
            while (count==0) {
                while ((current_line = bfr_filtro.readLine()) != null) {
                    words = current_line.split(" ");
                    for (String i : words) {
                        if (i.equals("ban")) {
                            count++;
                            System.out.println(true);
                        }
                    }
                }
            }
            System.out.println(false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


         */

    }

}


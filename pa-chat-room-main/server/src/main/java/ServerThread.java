package server.src.main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
    //Atributos estáticos
    //private final int port;
    private static ArrayList<ServerThread> threadList;
    private String nome;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private BufferedReader bfr_filtro; //Para ler palavras in filtro.txt
    private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
    private Socket con;
    private File filtro;
    private  String[] words;
    private FileReader fr;
    /**Method construtor
     * @param con of type Socket
     */
    public ServerThread(Socket con, ArrayList<ServerThread> threads){
        this.con = con;
        this.threadList = threads;
        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method run
     * When a client sends a msg, the server receives it and sends it to all clients
     */
    public void run ( ) {
        try {
                out = new PrintWriter(con.getOutputStream(),true);

                while (true) {
                    String msg;
                    OutputStream ou = con.getOutputStream();
                    Writer ouw = new OutputStreamWriter(ou);
                    BufferedWriter bfw = new BufferedWriter(ouw);
                    msg = "";
                    while (msg != null) {
                        msg = bfr.readLine();
                        if (msg!=null) {
                            sendToAll(msg);
                            System.out.println(msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    /**
     *Method to send message to all clients
     * @param msg of type String
     * @throws IOException
     */
    public void sendToAll( String msg) throws IOException {
        for (ServerThread sT : threadList)
            sT.out.println(msg);
    }



    public boolean filtro_palavras(String word_msg) throws IOException {
        filtro = new File("filtro.txt");
        fr = new FileReader(filtro);
        words = null;
        bfr_filtro = new BufferedReader(fr); //indicar pathname
        int count =0;
        String current_word;
        while (count==0) {
            while ((current_word = bfr_filtro.readLine()) != null) {
                words = current_word.split(" ");
                for (String i : words) {
                    if (i.equals(word_msg)) {
                        count++;
                        fr.close();
                        return true;
                    }
                }
            }
        }
        fr.close();
        return false;

    }

    public void server_log(String msg) {

    }





}



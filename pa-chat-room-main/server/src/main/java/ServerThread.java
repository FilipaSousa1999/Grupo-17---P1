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
    private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
    private Socket con;

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
                        String new_msg=msg;
                        if (msg!=null) {
                            String[] sentence = new_msg.split(" ",0);
                            for (String word : sentence) {
                                System.out.println(word);
                                //System.out.println(filtro_palavras(word)+" LOGIC IS");
                                if(filtro_palavras(word)) { //if true word in filtro
                                    msg = new_msg.replace("ban","*****");
                                    System.out.println(word + " THIS WORD WAS CHECKED");
                                }
                            }
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



    /**
     * @param word_msg entrada de palavra em mensagem
     * @return  true se a palavra fica em filtro
     * @throws IOException
     */
    public boolean filtro_palavras(String word_msg) throws IOException {
        System.out.println("IM CHECKING THIS WORD:  !"+word_msg+"!");
        File filtro = new File("C:\\Users\\Vasily Frolov\\Documents\\GitHub\\Grupo-17---P1\\pa-chat-room-main\\server\\filtro.txt");
        FileReader fr = new FileReader(filtro);
        String[] words = null; //array
        BufferedReader bfr_filtro = new BufferedReader(fr); //indicar pathname
        String current_line;
        while ((current_line = bfr_filtro.readLine()) != null) {
            words = current_line.split(" ");
            for (String i : words) {
                if (i.equals(word_msg)) {
                    fr.close();
                    System.out.println("BAN THIS WORD");
                    return true;
                } else
                    System.out.println("THIS WORD IS OK");
                }
            }
        fr.close();
        return false;

    }

    public void server_log(String msg) {

    }





}



package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class ServerThread extends Thread {
    private static ArrayList<ServerThread> threadList;
    private String client_id;
    private String nome;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
    private Socket con;
    private static Semaphore semaphore;
    private static Semaphore serlog_semaphore = new Semaphore(1);

    /**Method construtor
     * @param con of type Socket
     */
    public ServerThread(Socket con, ArrayList<ServerThread> threads, Semaphore semaphore){
        this.con = con;
        this.threadList = threads;
        this.semaphore = semaphore;
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
                    msg = bfr.readLine();
                    String new_msg = msg;
                    if (msg != null) {
                        if (msg.contains("left!")) {
                            sendToAll(msg);
                            semaphore.release();
                            threadList.remove(this);
                            return;
                        } else {
                            serlog_semaphore.acquire();
                            String[] arr_msg = msg.split(" ", 3);
                            String type = arr_msg[0];
                            System.out.println("TYPE IS " + type);
                            String client_id = arr_msg[1];
                            System.out.println("CLIENT ID IS " + client_id);
                            new_msg = arr_msg[2];
                            System.out.println("MESSAGE IS " + new_msg);
                            String[] sentence = new_msg.split(" ", 0);
                            for (String word : sentence) {
                                //System.out.println(word);
                                //System.out.println(filtro_palavras(word)+" LOGIC IS");
                                if (filtro_palavras(word)) { //if true word in filtro
                                    msg = new_msg.replace(word, "*****");
                                    System.out.println(word + " THIS WORD WAS CHECKED");
                                }
                            }
                            sendToAll(new_msg);
                            server_log(new_msg, type, client_id);
                            serlog_semaphore.release();
                            System.out.println(new_msg);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
            throw new RuntimeException(e);
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


    public String getClient_id(){
        return client_id;
    }

    public void setClient_id(String id){
        this.client_id=id;
    }

    /**
     * @param word_msg input of word that function will check
     * @return  true if a word in the filtro.txt
     * @throws IOException
     */
    public boolean filtro_palavras(String word_msg) throws IOException {
        System.out.println("IM CHECKING THIS WORD:  !"+word_msg+"!");
        File filtro = new File("./server/filtro.txt");
        FileReader fr = new FileReader(filtro);
        String[] words = null; //array
        BufferedReader bfr_filtro = new BufferedReader(fr); //indicar pathname
        String current_line;
        while ((current_line = bfr_filtro.readLine()) != null) {
            words = current_line.split(" ");
            for (String i : words) {
                if (i.equals(word_msg)) {
                    fr.close();
                    System.out.println(i);
                    System.out.println("BAN THIS WORD");
                    return true;
                } else
                    System.out.println("THIS WORD IS OK");
                }
            }
        fr.close();
        return false;

    }

    /**
     *
     * @param msg messeage from user if it exists
     * @param type what type of log
     * @param client_id id of client that did an action
     * @throws IOException
     */
    public void server_log(String msg, String type,String client_id) throws IOException {
        String [] a = msg.split(" ", 2);//save type and id of client
        String msg_log="";
        if (a[1]!=null) {
            msg_log = a[1];
        }
        File log = new File("./server/server.log");
        FileWriter wr = new FileWriter(log,true);
        if (type.equals("MESSAGE")) {
            String time1 = String.valueOf(java.time.LocalDateTime.now());
            wr.write(time1 + " - Action : " + type + " - CLIENT" + client_id + " - " + msg_log + " \r\n");
        } else {
            String time2 = String.valueOf(java.time.LocalDateTime.now());
            wr.write(time2 + " - Action : " + type + " - CLIENT" + client_id + " \r\n");
        }
        wr.flush();
        wr.close();
    }





}



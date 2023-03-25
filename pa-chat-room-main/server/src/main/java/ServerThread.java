package server.src.main.java;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;


public class ServerThread extends Thread {
    //Atributos estáticos
    private final int port;
    private static ArrayList<BufferedWriter> clientes;
    private String nome;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private BufferedReader bfr_filtro; //Para ler palavras in filtro.txt
    private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
    private Socket socket;
    private File filtro;
    private  String[] words;
    private FileReader fr;
    /**Method construtor
     * @param port of type Socket
     */
    public ServerThread ( int port ) {
        this.port = port;
        try {
            clientes = new ArrayList<>();
            server = new ServerSocket(this.port);
            System.out.println("O servidor está online, aguardando conexões...");

            socket = server.accept ( );
            System.out.println("Cliente conectou ao servidor.");


            in  = socket.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);



        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
    /**
     * Method run
     * When a client sends a msg, the server receives it and sends it to all clients
     */
    public void run ( ) {
        try {

            String msg;
            OutputStream ou = socket.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            msg="";
            while (msg!=null) {
                msg = bfr.readLine();
                sendToAll(msg);
                System.out.println(msg);
            }

    } catch ( IOException e ) {
        e.printStackTrace ( );
    }

    }
/**
 *Method to send message to all clients
 * @param msg of type String
 * @throws IOException
 */
    public void sendToAll( String msg) throws IOException {
        BufferedWriter bfa_writer;
        for (BufferedWriter bw : clientes) {
                String[] msg_words = msg.split(" ");
                StringBuffer msg_buf = new StringBuffer();
                int b= 0; // numero de palavra em mennsagem
                for (String word : msg_words) {
                    if(!filtro_palavras(word)) { //if false
                               msg.replaceAll("word","****");
                        }
                    msg_buf.append(" " + msg_words[b] + " ");
                    b++;
                }
                String msg_final = msg_buf.toString();
                if (!(msg==null)) {
                    //bw.write(nome + ": "+ msg + "\r\n");
                    bw.write( msg + "\r\n");
                    bw.flush();
                }
                        //  msg_buf.delete(0,msg_buf.length());
        }


    }


    /**
     * Verificar se palavra fica em file
     * @param word_msg palavra que nos recebemos
     * @return A verdadeira se palavra fica dentro file
     * @throws IOException
     */
    public boolean filtro_palavras(String word_msg) throws IOException {
        filtro = new File("C:\\Users\\Vasily Frolov\\Documents\\GitHub\\Grupo-17---P1\\pa-chat-room-main\\server\\filtro.txt");
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



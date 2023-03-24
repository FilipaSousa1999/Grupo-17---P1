package server.src.main.java;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
            clientes = new ArrayList<BufferedWriter>();
            server = new ServerSocket(this.port);
            System.out.println ( "Accepting Data" );
            socket = server.accept ( );
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
            OutputStream ou = this.socket.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            msg = bfr.readLine();

        while ( msg!=null) {
            sendToAll(msg);
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
    public void sendToAll(String msg) throws IOException {

        for (BufferedWriter bw : clientes) {
            //String[] msg_words = msg.split(" ");
            //StringBuffer msg_buf = new StringBuffer();
            //int b= 0; // numero de palavra em mennsagem
           // for (String word : msg_words) {
               // if(!filtro_palavras(word)) { //if false
             //       msg_words[b] = "****";
              //  }
              //      msg_buf.append(msg_words[b]);
              //      b++;
             //   }
            //String msg_final = msg_buf.toString();
            if (!(msg==null)) {
                bw.write(msg + "\r\n");
                msg = null;
                bw.newLine();
                bw.flush();
            }
          //  msg_buf.delete(0,msg_buf.length());
        }
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

}



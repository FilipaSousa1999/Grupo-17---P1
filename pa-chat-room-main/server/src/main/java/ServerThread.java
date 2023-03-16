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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.PrintWriter;


public class ServerThread extends Thread {
    //Atributos estáticos

    private final int quan_clintes;
    private final int port;
    private static ArrayList<BufferedWriter> clientes;
    private String nome;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private DataInputStream in;
    // private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
  //  private Socket con;
    private Socket socket;
    /**
     * Método construtor
     *
     * @param con do tipo Socket
     */
    /*
    public ServerThread(Socket con) {
        this.con = con;
        try {
            in = con.getInputStream();
            inr = new InputStremReader();
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */


    public ServerThread ( int port,int quan_clintes ) {
        this.port = port;
        this.quan_clintes = quan_clintes;

        try {
            server = new ServerSocket ( this.port );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }



    /**
     * Método run
     * Qd um cliente envia uma msg, o servidor recebe e manda para todos os clientes
     */
    /*
    public void run() {
        try {
            String msg;
            OutputStrem ou = this.con.getOutputStream();
            Writter ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            nome = msg = bfr.readLine();
            while (!"Sair".equalsIgnoreCasa(msg) && msg != null) {
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    */



    //O codigo de professor
    public void run ( ) {

        while ( true ) {
            try {
                System.out.println ( "Accepting Data" );
                socket = server.accept ( );  //Accept clients

                System.out.println("Connected");


                in = new DataInputStream ( socket.getInputStream ( ) );
                out = new PrintWriter ( socket.getOutputStream ( ) , true );
                String message = in.readUTF ( );
                System.out.println ( "***** " + message + " *****" );
                out.println ( message.toUpperCase ( ) );
                out.println("Welcome to Server");
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }

    }

}

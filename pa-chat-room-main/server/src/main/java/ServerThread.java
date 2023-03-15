import java.io.BufferedReader;
import  jaca.io.BufferedWriter;
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
    private static ArrayList<BufferedWriter> clientes;
    private String nome;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private InputStream in;
    private PrintWriter out;
    private static ServerSocket server;
    private Socket con;
    /**Método construtor
     * @param con do tipo Socket
     */
    public ServerThread ( Socket con ) {
        this.con = con;
        try {
            in = con.getInputStream();
            inr = new InputStremReader();
            bfr = new  BufferedReader(inr);
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
    /**
     * Método run
     * Qd um cliente envia uma msg, o servidor recebe e manda para todos os clientes
     */
    public void run ( ) {
        try {
            String msg;
            OutputStrem ou = this.con.getOutputStream();
            Writter ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            nome = msg = bfr.readLine();
        while ( !"Sair" .equalsIgnoreCasa(msg) && msg!=null ) {
          msg = bfr.readLine();
          sendToAll(bfw, msg);
          System.out.println(msg);

        }
    } catch ( IOException e ) {
        e.printStackTrace ( );
    }

}

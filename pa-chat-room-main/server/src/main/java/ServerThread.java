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
    /**Method construtor
     * @param con of type Socket
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
     * Method run
     * When a client sends a msg, the server receives it and sends it to all clients
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
/**
 *Method to send message to all clients
 * @param bwSaida of type BufferedWriter
 * @param msg of type String
 * @throws IOException
 */
public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
    BufferedWriter bwS;
    for (BufferedWriter bw : clientes) {
        bwS = (BufferedWriter) bw;
        if (!(bwSaida == bwS)) {
            bw.write(nome + " -> " + msg + "\r\n");
            bw.flush();
        }
    }
}
    public static void conecao ( String[] args ) {
        //ServerThread server = new ServerThread ( 8888 );
        try {
            //creates the objects to instantiate the server
            JLabel lblMessage = new JLabel ("Porta do servidor: ");
            JTextField txtPorta = new JTextField("8888");
            Object[] texts = {lblMessage, txtPorta};
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getTest()));
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessage(null, "Servidor ativo na porta: ")+txtPorta.getText());

            while (true){
                System.out.println("Aguardar conexão...");
                Socket con = server.accept();
                System.out.println ("Cliente conetado...");
                Thread t = new Servidor (con);
                t.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}



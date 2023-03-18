package client.src.main.java;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ClientThread extends Thread {

    private final int port;
    private final int id;
    //private final int freq;
    private Writer ouw;
   private DataOutputStream out; //Enviar message
    private BufferedReader in; //Ler message
    private Socket socket;//Connection
    private BufferedWriter bfw;
    private String send_message;
    private String receive_message;
    private String ClientName;
    private Entra_Frame f;
    private Chat_Frame chat;
    private JFrame ver;
    private JButton ok;

    private JTextArea text;

    public ClientThread ( int port , int id) {

        Entra_Frame f = new Entra_Frame();


        this.port = port;
        this.id = id;
        //this.freq = freq;
        chat = new Chat_Frame();
        chat.setClient_name(f.getName());


    }


//Ver como enviar e receber mensagens


    public void run ( ) {
        //try {
        String MsgFromClient;
        while ( true ) {
            System.out.println ( "Sending Data" );
            try {

                // if(sem.tryAcquire(1, TimeUnit.SECONDS)) {
                socket = new Socket ( "localhost" , port );
                out = new DataOutputStream ( socket.getOutputStream ( ) ); //conection
                ouw = new OutputStreamWriter(out);
                bfw = new BufferedWriter(ouw);

               // out.writeUTF ( "My message number "  + " to the server " + "I'm " + id );

              //  String response; //msg
               // response = in.readLine ( );

               // System.out.println ( "From Server " + response );
                bfw.flush ( );
                //socket.close ( );
                // sleep ( freq );


                //String msg = in.readLine();

                /*
                Mes m = new Mes();
                m.start();
                receive_message=in.readLine();

                if(receive_message.contains(send_message)) {
                    System.out.println(receive_message);
                }
                */
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }


    }



    public void enviar_to_server(String msg, Chat_Frame chat) {
        try
        {
            bfw.write(msg+"/r/n");
            chat.get_area().append(ClientName +":" + chat.getUser_msg().getText() + "\r\n");
        } catch (IOException e) {
            try {
                bfw.flush();
                chat.getUser_msg().setText("");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public void listen_to_server(Chat_Frame chat) {
        try {
            in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
            String msg = "";
            if(in.ready()) {
                msg = in.readLine();
            }
            chat.get_area().append(msg+"\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void CloseThread() {
        try {
            if(this.in != null) {
                this.in.close();
            }
            if(this.out != null) {
                this.out.close();
            }
            if(this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionPerformed(ActionEvent e,Chat_Frame chat){
        try {
            if(e.getActionCommand().equals(chat.getBtnSend()))
                enviar_to_server(chat.getUser_msg().getText(), chat);
             else
                if(e.getActionCommand().equals(chat.getBtnExit()))
                    CloseThread();
            } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void KeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enviar_to_server(chat.getUser_msg().getText(),chat );
        }
    }

}

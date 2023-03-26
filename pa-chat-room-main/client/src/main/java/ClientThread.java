package client.src.main.java;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private final int port;
    private final int id;
    //private final int freq;
    private Writer ouw;
    private OutputStream out; //Enviar message
    private BufferedReader in; //Ler message
    private Socket socket;//Connection
    private BufferedWriter bfw;
    private String send_message;
    private String receive_message;
    private String ClientName;
    private Chat_Frame chat;
    private JFrame ver;
    private JButton ok;

    private JTextArea text;

    public ClientThread ( int port , int id) {


        this.port = port;
        this.id = id;
        chat = new Chat_Frame();

    }


    public BufferedWriter getBfw() {
        return bfw;
    }

    public void run ( ) {

        try {
            socket = new Socket("localhost", port);
            chat.set_area("SERVER: Bem-vindo " + chat.getClient_name() + "\r\n");
            out = socket.getOutputStream();
            ouw = new OutputStreamWriter(out);
            bfw = new BufferedWriter(ouw);
            bfw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // if(sem.tryAcquire(1, TimeUnit.SECONDS)) {
        System.out.println("Sending Data");
        while (true) {
            listen_to_server(chat);
            Action(chat);
            if (socket.isClosed()) {
                return;
            }
        }


    }


    public void enviar_to_server(String msg, Chat_Frame chat) {
        try
        {
            bfw.write(chat.getClient_name()+": "+msg+"\r\n");
            //bfw.write(msg+"\r\n");
            //bfw.newLine();
            //chat.get_area().append(chat.getClient_name()+": " + chat.getUser_msg().getText() + "\r\n");
            bfw.flush();
            chat.getUser_msg().setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void listen_to_server(Chat_Frame chat) {
        try {
            in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
            String msg_print = "";
            if(in.ready()) {
                msg_print = in.readLine();
                if (!(msg_print==null))
                    chat.set_area(msg_print+"\r\n");
                //msg_print=null;
                //in.close();
            }

        } catch (IOException e) {
            CloseThread();
        }
    }


    public void CloseThread() {
        try {
            bfw.write(chat.getClient_name()+" has left! \r\n");
            this.bfw.close();
            this.ouw.close();
            this.out.close();
            this.in.close();
            this.socket.close();
            chat.setVisible(false);
            chat.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Action(Chat_Frame chat){
        try {
            if(chat.isBtnSend_isClicked()) {
                enviar_to_server(chat.getUser_msg().getText(), chat);
                chat.setBtnSend_isClicked(false);
            } else if(chat.isBtnExit_isClicked()){
                CloseThread();
                chat.setBtnExit_isClicked(false);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}

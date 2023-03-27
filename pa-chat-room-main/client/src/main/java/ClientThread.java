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

    public String  get_id() {
        return String.valueOf(this.id);
    }

    public BufferedWriter getBfw() {
        return bfw;
    }

    /**
     * Method run where client send and recieve messagers from serverand also its chat
     */
    public void run ( ) {

        try {
            socket = new Socket("localhost", port);
            out = socket.getOutputStream();
            ouw = new OutputStreamWriter(out);
            bfw = new BufferedWriter(ouw);
            bfw.flush();
            enviar_to_server("SERVER: Bem-vindo " + chat.getClient_name(), this.chat, "CONNECTION", get_id());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // if(sem.tryAcquire(1, TimeUnit.SECONDS)) {
        System.out.println("Sending Data");
        while (true) {
            listen_to_server(chat);
            Action(chat);
        }


    }


    /** function where client can send messages
     * @param msg message that wi will send
     * @param chat we get name of client from chat
     * @param type type of message that function sends
     * @param id id of client that function sends
     */
    public void enviar_to_server(String msg, Chat_Frame chat,String type, String id ) {
        try
        {
            if (msg.startsWith("SERVER"))
                bfw.write( type + " "+ id + " " +msg+"\r\n");
            else
                bfw.write( type + " "+ id + " " + chat.getClient_name()+": "+msg+"\r\n");
            //bfw.write(msg+"\r\n");
            //bfw.newLine();
            //chat.get_area().append(chat.getClient_name()+": " + chat.getUser_msg().getText() + "\r\n");
            bfw.flush();
            chat.getUser_msg().setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /** function for recieve messegers from chat
     * @param chat frame of chat that we are using to print a message
     */
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


    /**
     * its closing ClientThread
     */
    public void CloseThread() {
        try {
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

    /** when we have any event we use this function
     * @param chat frame of chat that we are using
     */
    public void Action(Chat_Frame chat){
        try {
            if(chat.isBtnSend_isClicked()) {
                enviar_to_server(chat.getUser_msg().getText(), chat,"MESSAGE",get_id());
                chat.setBtnSend_isClicked(false);
            } else if(chat.isBtnExit_isClicked()){
                enviar_to_server("SERVER: Saiu " + chat.getClient_name(), chat,"DISCONNECTED",get_id());
                CloseThread();
                chat.setBtnExit_isClicked(false);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}

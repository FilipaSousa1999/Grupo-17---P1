package main.java;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private final int port;
    private final int id;
    private Writer ouw;
    private OutputStream out;
    private BufferedReader in;
    private Socket socket;
    private BufferedWriter bfw;
    private Chat_Frame chat;


    public ClientThread ( int port , int id) {


        this.port = port;
        this.id = id;
        chat = new Chat_Frame();

    }

    public String  get_id() {
        return String.valueOf(this.id);
    }

    /**
     * Method run where client send and recieve messagers from serverand also its chat
     */
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
        System.out.println("Sending Data");
        while (true) {
            listen_to_server(chat);
            Action(chat);
            if (socket.isClosed()) {
                return;
            }
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
            String msg_print;
            if(in.ready()) {
                msg_print = in.readLine();
                if (!(msg_print==null))
                    chat.set_area(msg_print+"\r\n");
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

    /** when we have any event we use this function
     * @param chat frame of chat that we are using
     */
    public void Action(Chat_Frame chat){
        try {
            if(chat.isBtnSend_isClicked()) {
                enviar_to_server(chat.getUser_msg().getText(), chat,"MESSAGE",get_id());
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

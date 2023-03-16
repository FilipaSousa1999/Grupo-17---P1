package main.java;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {

    private final int port;
    private final int id;
    private final int freq;
    private DataOutputStream out; //Enviar message
    private BufferedReader in; //Ler message
    private Socket socket;//Connection

    private String send_message;
    private String receive_message;
    private String ClientName;

    public ClientThread ( int port , int id , int freq ) {
        this.port = port;
        this.id = id;
        this.freq = freq;
    }


//Ver como enviar e receber mensagens


    public void run ( ) {
        //try {
        String MsgFromClient;
        int i = 0;
        while ( true ) {
            System.out.println ( "Sending Data" );
            try {
                // if(sem.tryAcquire(1, TimeUnit.SECONDS)) {
                socket = new Socket ( "localhost" , port );
                out = new DataOutputStream ( socket.getOutputStream ( ) );
                in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
                out.writeUTF ( "My message number " + i + " to the server " + "I'm " + id );

                String response; //msg
                response = in.readLine ( );

                System.out.println ( "From Server " + response );
                out.flush ( );
                socket.close ( );
                sleep ( freq );


                //String msg = in.readLine();

                /*
                Mes m = new Mes();
                m.start();
                receive_message=in.readLine();

                if(receive_message.contains(send_message)) {
                    System.out.println(receive_message);
                }
                */
                i++;
            } catch ( IOException | InterruptedException e ) {
                e.printStackTrace ( );
            }
        }


    }





     void sendMessage(String m){   //enviar message
        try{
            out.writeUTF ( m );
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void CloseThread(Socket socket, BufferedReader bufferedReader,  DataOutputStream dataOutputStream) {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(dataOutputStream != null) {
                dataOutputStream.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public class Mes extends Thread {

        public void run(){
            try {
                while (true) {
                    System.out.println("Input a sentence: ");
                    //read a sentence from the standard input
                    send_message = in.readLine();
                    //Send the sentence to the server
                    sendMessage(send_message);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }



}

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private String ClientName;
    private final int port;
    private final int id;
    private final int freq;
    private DataOutputStream out;
    private BufferedReader in;
    private Socket socket;

    public ClientThread ( int port , int id , int freq ) {
        this.port = port;
        this.id = id;
        this.freq = freq;
        this.ClientName = in.readLine();
        broadcastMsg("SERVER:" + this.ClientName + "entra em chat");
    }


    private String getName() {
        return ClientName;
    }









    public void run ( ) {
        //try {
        int i = 0;
        while ( true ) {
            System.out.println ( "Sending Data" );
            try {
                // if(sem.tryAcquire(1, TimeUnit.SECONDS)) {
                socket = new Socket ( "localhost" , port );
                out = new DataOutputStream ( socket.getOutputStream ( ) );
                in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
                out.writeUTF ( "My message number " + i + " to the server " + "I'm " + id );
                String response;
                response = in.readLine ( );
                System.out.println ( "From Server " + response );
                out.flush ( );
                socket.close ( );
                sleep ( freq );
                i++;
            } catch ( IOException | InterruptedException e ) {
                e.printStackTrace ( );
            }
        }

        String MsgFromClient;
        while (socket.isConnected()) {   //enviar mensagens
            try {
                MsgFromClient = in.readLine();
                broadcastMsg(MsgFromClient);
            } catch (IOException e) {
                CloseThread(socket,in,out);
                break;
            }
        }

    }


    //Enviar mensagens
    public void broadcastMsg(String message) {    //broadcast
        for (ClientThread Client : clientThread) {
            try {
                if (!Client.getName.equals(this.getName())) {
                    Client.out.write(message);
                    Client.out.newLine();
                    Client.out.flush();
                }

            } catch (IOException e) {
                CloseThread(this.socket, this.in,this,out);
            }

        }

    }


    public void removeClientThread() {        //alguem saiu 
        ClientThreads.remove(this);  //array com clients
        broadcastMsg("SERVER:" + this.ClientName + "saiu de chat");
    }


    public void CloseThread(Socket socket, BufferedReader bufferedReader,  DataOutputStream dataOutputStream) {
        removeClientThread();
        try {
            if(bufferedReader != NULL) {
                bufferedReader.close();
            }
            if(dataOutputStream != NULL) {
                dataOutputStream.close();
            }
            if(socket != NULL) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

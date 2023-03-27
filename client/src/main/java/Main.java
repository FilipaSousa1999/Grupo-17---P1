package main.java;



public class Main {

    public static void main ( String[] args ) {



       ClientThread client = new ClientThread ( 8888 , 1);
       client.start();

       ClientThread client2 = new ClientThread ( 8888 , 2);
       client2.start();

       //ClientThread client3 = new ClientThread ( 8888 , 3);
       //client3.start ( );

        //ClientThread client4 = new ClientThread ( 8888 , 4);
        //client4.start ( );
    }
}

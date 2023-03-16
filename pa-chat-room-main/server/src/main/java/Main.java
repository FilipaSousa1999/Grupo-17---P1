package server.src.main.java;

public class Main {

    public static void main ( String[] args ) {
        ServerThread server = new ServerThread ( 8888,6 );
        server.start ();
        System.out.println("Hi");
    }
}

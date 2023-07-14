import java.io.*;
import java.net.*;

public class ServidorTCP {

    public static void main(String[] args) {
        int puerto = 1234;
        Socket sc = null;
        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                //Espero a que un cliente se conecte
                sc = serverSocket.accept();
                System.out.println("Cliente Conectado");

            }

        } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

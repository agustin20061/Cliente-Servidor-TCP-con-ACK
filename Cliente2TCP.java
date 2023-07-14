
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente2TCP {

    public static void main(String[] args) {
        String servidor = "localhost";
        Servidor s1 = new Servidor();
        int puerto = 1234;
        try {
            Socket socket = new Socket(servidor, puerto);
            System.out.println("Conectado al servidor " + servidor + " en el puerto " + puerto);

            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salidaServidor = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));

            // Leer mensajes del usuario y enviarlos al servidor
            String mensaje;
            //A cual ip se lo manda
            InetAddress ips;
            //tengo q poner lo del scanner
            for (InetAddress ip : s1.getIps()) {
                if (ip == ips) {
                    System.out.println("esta en el servidor");

                } else{
                    System.out.println("no esta en el Servidor");
                }
            }

                    do {
                        System.out.print("mensaje a "+ips+" - Mensaje a enviar (o 'salir' para terminar):+ ");
                        mensaje = entradaUsuario.readLine();
                        salidaServidor.println(mensaje);
                    } while (!mensaje.equals("salir"));

                    socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}


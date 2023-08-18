import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    private static final String ipServidor = "127.0.0.1"; // Cambia esta IP por la del servidor
    private static final int puertoServidor = 5433;
    static class hiloMandador extends Thread {
        private PrintWriter out;//cliente/servidor
        public hiloMandador(PrintWriter out) {
            this.out = out;
        }

        public void run() {
            Scanner entrada=new Scanner(System.in);
            try {
                while (true) {
                    System.out.print("Mensaje: \n");
                    String mensaje = entrada.nextLine();
                    out.println(mensaje);

                    if (mensaje.equalsIgnoreCase("salir")) {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
            static class hiloReceptor extends Thread {
                private BufferedReader HRin;//servidor/cliente


        public hiloReceptor(BufferedReader In) {
                this.HRin = In;
            }

        public void run() {
            try {
                    String respuestaServidor;
                    while ((respuestaServidor=HRin.readLine())!=null){
                        System.out.println("Respuesta: "+ respuestaServidor);
                    }
                } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        try (Socket s = new Socket(ipServidor, puertoServidor);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine()); // Mensaje de bienvenida
            String nombre= scanner.nextLine();
            out.println(nombre);//les manda el mensaje

            String respuesta = in.readLine();//lee la rta
            System.out.println(respuesta);

            hiloMandador hiloMan = new hiloMandador(out);
            hiloReceptor hiloRec=new hiloReceptor(in);
            hiloMan.start();
            hiloRec.start();
            hiloMan.join();
            hiloRec.join();
            } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
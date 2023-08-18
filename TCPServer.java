import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static final int PORT = 5433;
    private static HashSet<hiloCliente> listaClientes = new HashSet<>();
    static class hiloCliente extends Thread {
        private Socket s;
        private PrintWriter out;
        private BufferedReader in;
        private String nombreCliente;

        public hiloCliente(Socket socket) {
            this.s= socket;
        }

        public void run() {
            try {
                out = new PrintWriter(s.getOutputStream(), true);//servidor/cliente
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));//cliebte//servidor

                out.println("te conectaste al servidor. pone tu nombre: ");
                nombreCliente = in.readLine();
                out.println("Hola " + nombreCliente + " podes enviar mensajes");
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    if (mensaje.equals("salir")) {
                        out.println("Adiós, " + nombreCliente);
                        break;
                    } else if (mensaje.startsWith("@")) {
                        String[] partes = mensaje.split(" ", 2);
                        String nombreClienteRecibio = partes[0].substring(1);
                        String mensajePrivado = partes[1];
                        mensajePrivado(nombreCliente, nombreClienteRecibio, mensajePrivado);
                    } else {
                        mensajePublico(nombreCliente + ": " + mensaje);
                    }
                    out.println("ACK");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("te fuiste del servidor " + nombreCliente);
                    s.close();
                    listaClientes.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void mensajePrivado(String nombreClienteEnvio, String nombreClienteRecibio, String mensaje) {
            for (hiloCliente client : listaClientes) {
                if (client.nombreCliente.equals(nombreClienteRecibio)) {
                    client.out.println("[Mensaje privado de " + nombreClienteEnvio + "]: " + mensaje);
                    break;
                }
            }
        }

        private void mensajePublico(String mensaje) {
            for (hiloCliente cliente : listaClientes) {
                cliente.out.println(mensaje);
            }
        }
    }
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT);

            while (true) {
                Socket sCliente = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + sCliente);

                hiloCliente hc1 = new hiloCliente(sCliente);
                listaClientes.add(hc1);
                hc1.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
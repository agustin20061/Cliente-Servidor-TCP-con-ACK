import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public class Programa {
    public Programa() {
    }

    public void startProgram() throws IOException, NombreExc {
        DatagramSocket socket=new DatagramSocket();

        Properties prop = new Properties();
        String fileName = "app.config";  // nombre del archivo de configuracion
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            System.out.println("El archivo de configuracion existe");
        }
        catch (FileNotFoundException ex) {
            System.out.println("El archivo de configuracion no existe");
        }
        catch (IOException ex) {
            System.out.println("Error de acceso en el archivo de configuracion");
        }
        BufferedReader lectorConsola = new BufferedReader(new InputStreamReader(System.in));
        String nombreIngresado;
        System.out.println("ingresa tu nombre: ");
        nombreIngresado=lectorConsola.readLine();
        HashSet<String>listaNombres=new HashSet<>();
        String nombresAgregados= prop.getProperty("Nombre");
        String[] separacion=nombresAgregados.split("<>");


        for (int i=0;i<= separacion.length-1;i++){
            String nombrePerArchivo=separacion[i];

            listaNombres.add(nombrePerArchivo);
        }
        Boolean verofal=true;
        for (String nom:listaNombres) {

            if (nombreIngresado.equals(nom)){
                System.out.println("el nombre esta en el archivo de configuracion");
                verofal=true;
                break;
            }else {
                verofal=false;
            }
        }
        if (!verofal){
            throw new NombreExc("el nombre no esta en el archivo de configuracion");
        }


        System.out.println("escriba un mensaje: ");
        String mensaje=lectorConsola.readLine();
        mandarMensaje(mensaje,prop,nombreIngresado,socket);


        //DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipDestino), puertoDestino);
        //socket.send(packet);




       /* if (posicionNombreIngresado==0||posicionNombreIngresado==2){
            String contacto = prop.getProperty(aTopo[1]);
            String ip=contacto.split(":")[0];
            int puerto= Integer.parseInt(contacto.split(":")[1]);
            TCPClient cliente=new TCPClient(ip,puerto);
            cliente.start(ip,puerto);
        }else {
            String dirServidor=prop.getProperty(aTopo[1]);
            int puerto= Integer.parseInt(dirServidor.split(":")[1]);
            TCPServer servidor=new TCPServer(puerto);
            servidor.start(puerto);
        }
*/
    }
    public void mandarMensaje(String mensaje,Properties prop,String nombreIngresado,DatagramSocket socket) throws IOException {
        String[] partes = mensaje.split(" ", 2);
        String nombreClienteRecibio = partes[0].substring(1);
        String mensajePrivado = partes[1];

        //esta dividido los nombres en posiciones sabemos en la posicion que esta el nombre a mandar el msj
        String topologia = prop.getProperty("Topologia");
        String[] aTopo = topologia.split("<->");
        int posicionNombreRecibido = Arrays.binarySearch(aTopo, nombreClienteRecibio);
        System.out.println(posicionNombreRecibido);//posicion del nombre a mandar el msj
        int posicionNombreIngresado= Arrays.binarySearch(aTopo, nombreIngresado);
        System.out.println(posicionNombreIngresado);//posicion del nombre ingresado
        int difPosiciones=posicionNombreRecibido-posicionNombreIngresado;
        System.out.println(difPosiciones);
        if (difPosiciones==1||difPosiciones==-1){
            // Construir mensaje con el formato "origen:destino:mensaje"
            String mensajeCompleto = nombreIngresado + ":" + nombreClienteRecibio+":"+mensajePrivado;
            byte[] buffer = mensajeCompleto.getBytes();
            String contacto = prop.getProperty(aTopo[posicionNombreRecibido]);
            String ipDestino=contacto.split(":")[0];
            int puertoDestino= Integer.parseInt(contacto.split(":")[1]);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipDestino), puertoDestino);
            //socket.send(packet);
        } else if (difPosiciones>1||difPosiciones<-1) {
            // Construir mensaje con el formato "origen:destino:mensaje"
            String mensajeCompleto = nombreIngresado + ":" + nombreClienteRecibio+":"+mensajePrivado;
            byte[] buffer = mensajeCompleto.getBytes();
            String contacto = prop.getProperty(aTopo[posicionNombreRecibido]);
            String ipDestino=contacto.split(":")[0];
            int puertoDestino= Integer.parseInt(contacto.split(":")[1]);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipDestino), puertoDestino);
            //socket.send(packet);
        }


    }
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NombreExc {
        Programa programa=new Programa();
        programa.startProgram();
    }

}

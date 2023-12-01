import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

public class Programa3 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
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
        String nombre;
        System.out.println("ingresa tu nombre: ");
        nombre=lectorConsola.readLine();
        String topologia = prop.getProperty("Topologia");
        String[] aTopo = topologia.split("<->");
        int posicion = Arrays.binarySearch(aTopo, nombre);

        if (posicion==0||posicion==2){
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

    }
}

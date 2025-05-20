import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteAdivina {
    
    public static void main(String[] args) {
        
    //nombre host
    String host="localhost"; //127.0.0.1
    //puerto
    int puerto=6000;
    //Socket para la conexión TCP
    Socket socketServicio;
    PrintWriter outPrinter;
    BufferedReader inReader;
    Scanner capt;

    String resultado="";
    int miNumero;
    
    //Envío y recepción
  //  char []bufferEnvio;
  //  char []bufferRecepcion=new char[256];

        System.out.println("Nos toca adivinar!!!! entre el 1 y el 10" );
        //Nos conectamos al servidor
        try {
            socketServicio =new Socket(host, puerto);
            //Creamos flujo de salida al servidor
            PrintWriter fsalida=new PrintWriter(socketServicio.getOutputStream(),true);
            //Creamos el flujo de entrada
            BufferedReader fentrada=new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

            capt=new Scanner(System.in);

            //Empieza la lógica del juego
             do{ 
                System.out.println("Introduce el número: ");
                miNumero = capt.nextInt();
                fsalida.print(miNumero);//Enviamos el número
               // fsalida.flush();

                resultado=fentrada.readLine();//Recogemos
                System.out.println("Recibido: "+resultado);

             }while(!(resultado.equals("ACERTASTE")));

             socketServicio.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }//main



}//ClienteAdivina

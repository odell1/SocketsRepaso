import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class AdivinaNumero extends Thread{

    //Variables
    private final Socket socketServicio;
    private final Random random;
    private final int numeroOculto;
    private boolean acierto=false;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

        PrintWriter outPrinter;   
    BufferedReader inReader;




    //////
    /// Cogemos el Socket que nos pasa el servidor
    /// Creamos el número aleatorio hsta el 10;
    /// 
    public AdivinaNumero(Socket socket) {
        this.socketServicio=socket; 
        random=new Random();
        numeroOculto=random.nextInt(10)+1;// 1 - 10
        // Creamos los flujos de entrada y salida 
        try {
            inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
        } catch (IOException e) {
            System.err.println("Errorcillo al obtener los flujos de E/S.");
        }//try

    }// Constructor

    /// Lógica del programa
    public void run(){
        // nuevo
        char[] datosRecibidos=new char[256];
        int bytesRecibidos=0;
        char[] datosEnvio;
        String respuesta="";

        //Viejo
        System.out.println("El número a adivinar "+ numeroOculto);
       // String cadena="";
       // String cadenaMayor="El número ha de ser mayor";
      //  String cadenaMenor="El número ha de ser menor";
       //  String cadenaAcierto="ACERTASTE";
        int intentos=0;

        
        while (intentos!=5 && acierto!=true) {
            intentos++;
           
            try {
                
                bytesRecibidos=inReader.read(datosRecibidos);
                int numero=Integer.parseInt(new String(datosRecibidos,0,bytesRecibidos));

                if(numero<numeroOculto)
                    respuesta="Fallaste, el número debe ser mayor";
                else if(numero>numero)
                    respuesta="Fallaste, el número debe ser menor";
                else if (numero==numeroOculto){
                    respuesta="ACERTASTE";
                    acierto=true;
                }
                outPrinter.print(respuesta);
                outPrinter.flush();

            } catch (IOException e) {

                e.printStackTrace();
            }//try
            
        }//while

        try {
            socketServicio.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//run


}//AdivinaNumero

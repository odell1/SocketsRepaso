package Modificado;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class AdivinaNumero extends Thread{

    //Variables
    private final Socket socketServicio;
    private final Random random;
    private final int numeroOculto;
    private boolean acierto=false;
    PrintWriter outPrinter;
    BufferedReader inReader;

    ///////
    /// Cogemos el Socket que nos pasa el servidor
    /// Creamos el número aleatorio hasta el 10;
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
        String respuesta="";

        //Viejo
        System.out.println("El número a adivinar "+ numeroOculto);
        int intentos=0;

        while (intentos!=5 && acierto!=true) {
            intentos++;

            try {
                String linea = inReader.readLine();
                int numero = Integer.parseInt(linea);

                if(numero < numeroOculto)
                    respuesta="Fallaste, el número debe ser mayor";
                else if(numero > numeroOculto)
                    respuesta="Fallaste, el número debe ser menor";
                else if (numero == numeroOculto){
                    respuesta="ACERTASTE";
                    acierto=true;
                }
                outPrinter.println(respuesta);

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

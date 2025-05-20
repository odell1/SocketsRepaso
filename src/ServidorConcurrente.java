
//Servidor Concurrente

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorConcurrente {
    public static void main(String[] args) throws Exception {
       
        int puerto=6000;
        ServerSocket socketServidor;
        Socket socketServicio;
        System.out.println("Servidor arrancadito..... \n");

        try {
            socketServidor=new ServerSocket(puerto);
            do{
                socketServicio=socketServidor.accept();//Aceptamos las conexiones
                //Creo el hilo para la comunicación. 
                AdivinaNumero adivina=new AdivinaNumero(socketServicio);
                adivina.start();// ----> Este es el hilo

            }while(true);
        } catch (Exception e) {
            System.err.println("Judas cascó");
        }//try

    }//main
}//ServidorConcurrente

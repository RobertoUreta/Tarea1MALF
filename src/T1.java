
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fernanda
 */
public class T1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       /*
        Leer ER y cadena
        */
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        String regex = br.readLine();
        String cadena = br.readLine();
        
        /*
        Escribir la expresion regular en notacion posfija
        */
        String postfixER = InfixToPostfix.infixToPostfix(regex);
        
        /*
        Crear un objeto de la clase ER
        */
        ER er = new ER(postfixER);
        
        /*
        Construccion el automata finito no determinista
        */
        AFND afnd = new AFND(er, true);
        
        System.out.println("AFND: ");
        afnd.obtenerAFND().imprimirThc();
        
        /*
        construccion del automata finito determinista con sigma estrella por delante 
        */
        AFD afd = new AFD(afnd.obtenerAFND());

        System.out.println("AFD: ");
        afd.imprimirAFD();
        
        /*
        Busqueda de las coincidencias
        */
        System.out.println("Coincidencias:");
        AFND afndSinEstrella = new AFND(er, false);
        AFD afdSinEstrella = new AFD(afndSinEstrella.obtenerAFND());
        Coincidencias.buscarCoincidencias(afd, afdSinEstrella, cadena);//Coincidencias.buscarCoincidencias(afd,cadena);
    }

}

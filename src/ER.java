
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fernanda
 */
public class ER {
    //Character sigma[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','Y','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9'}; //Contiene todas las letras de sigma
    private ArrayList<Character>op;
    
    private ArrayList<Character> alfabetoER; //Contiene solo las letras que aparecen en la ER
    
    private String ER;

    public ER(String ER) {
        this.ER = ER;
        this.alfabetoER = new ArrayList<>();
        this.op = new ArrayList<>();
        op.add('.');
        op.add('|');
        op.add('*');
       
        this.obtenerAlfabeto();
    }
    
    private void obtenerAlfabeto(){
        for (int i = 0; i < ER.length(); i++) {          
            char c = ER.charAt(i);
            if (!op.contains(c) && !this.alfabetoER.contains(c)){
                this.alfabetoER.add(c);
            }
            
        }
    }
    
    public ArrayList<Character> obtenerOperadores(){
        return this.op;
    }
    
    public int lengthER(){
        return this.ER.length();
    }
    
    public char charAtER(int i){
        return this.ER.charAt(i);
    }
    
    
    public boolean esDelAlfabetoDeER(char c){
        if(this.alfabetoER.contains(c)){
            return true;
        }
        return false;
    }
    
    public ArrayList<Character> obtenerAlfabetoER(){
        return this.alfabetoER;
    
    }
    
}

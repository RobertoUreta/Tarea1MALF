
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roberto
 */
public class AFD {

    Thomson AFND;
    ArrayList<Integer> K;//ESTADOS
    ArrayList<Character> sigma;//ALFABETO
    int s;//ESTADO INICIAL
    ArrayList<Integer> F;//ESTADO FINAL
    HashMap<Integer, ArrayList<Transition>> delta;//DELTA
    int numEstado;

    public AFD(Thomson AFND) {
        
        this.AFND = AFND;
        this.K = new ArrayList<>();
        this.sigma = new ArrayList<>();
        this.F = new ArrayList<>();
        this.delta = new HashMap<>();
        this.numEstado = 0;
        generarAFD();
        //System.out.println("AFD:");
        //this.imprimirThc();
    }

    private void generarAFD() {
        ArrayList<Integer> estadoInicial = clausuraEpsilon(this.AFND.getS());
        HashMap<Integer, ArrayList<Integer>> estadosAFD = new HashMap<>();
        estadosAFD.put(this.numEstado++, estadoInicial);
        HashMap<Integer, ArrayList<Integer>> estadosFinales = conversion(estadoInicial, estadosAFD);
        obtenerEstadosAFD(estadosFinales);
        determinarEstadosFinales(this.AFND.getF(0), estadosFinales);
        this.sigma = obtenerSigma();
    }

    private void obtenerEstadosAFD(HashMap<Integer, ArrayList<Integer>> estadosFinales) {
        Iterator it = estadosFinales.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            this.K.add(key);
        }
    }
    
    private ArrayList<Integer>estadosAlosQueLlegaS(){
        ArrayList<Integer>estadosPro = new ArrayList<>();
        for (Integer key : delta.keySet()) {
            for (Transition t : delta.get(key)) {
                if(t.l=='$'){
                    if(!estadosPro.contains(t.target)){
                        estadosPro.add(t.target);
                    }
                    
                }
            }
        }
        return estadosPro;
    }
    private void determinarEstadosFinales(int f, HashMap<Integer, ArrayList<Integer>> estadosFinales) {
        Iterator it = estadosFinales.keySet().iterator();
        ArrayList<Integer>eals = this.estadosAlosQueLlegaS(); //Estados a los que llego con $
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            if (estadosFinales.get(key).contains(f)) {
                this.F.add(key);
            }
        }
    }

    public int getS() {
        return this.s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int sizeF() {
        return this.F.size();
    }

    public Integer getF(int i) {
        return this.F.get(i);
    }

    public boolean addF(Integer e) {
        return this.F.add(e);
    }

    public void imprimirAFD() {
        System.out.print("K={");
        for (int i = 0; i < K.size(); i++) {
            if (i == K.size() - 1) {
                System.out.print("q" + K.get(i));
            } else {
                System.out.print("q" + K.get(i) + ",");
            }

        }
        System.out.println("}");

        System.out.print("Sigma={");
        for (int i = 0; i < this.sigma.size(); i++) {
            if (i == this.sigma.size() - 1) {
                System.out.print(this.sigma.get(i));
            } else {
                System.out.print(this.sigma.get(i) + ",");
            }

        }
        System.out.println("}");
        System.out.println("Delta:");

        for (Integer key : this.delta.keySet()) {
            if (this.delta.get(key) != null) {
                for (Transition t : this.delta.get(key)) {
                    System.out.println("(q" + key + "," + t.getL() + ",q" + t.getTarget() + ")");
                }
            }

        }

        System.out.println("s=q" + this.s);
        System.out.print("F={");
        for (int i = 0; i < this.F.size(); i++) {
            if (i == this.F.size() - 1) {
                System.out.print("q" + this.F.get(i));
            } else {
                System.out.print("q" + this.F.get(i) + ",");
            }

        }
        System.out.println("}");

        System.out.println("");
    }

    public int sizeK() {
        return this.K.size();
    }

    public Integer getK(int i) {
        return this.K.get(i);
    }

    public boolean addK(Integer e) {
        return this.K.add(e);
    }

    public int getNumEstado() {
        return this.numEstado;
    }

    public void setNumEstado(int numEstado) {
        this.numEstado = numEstado;
    }

    private ArrayList<Character> obtenerSigma() {
        ArrayList<Character> letras = new ArrayList<>();
        for (int i = 0; i < this.AFND.sizesigma(); i++) {
            letras.add(this.AFND.getSigma(i));
        }
        return letras;
    }

    private ArrayList<Integer> clausuraEpsilon(int s) {
        ArrayList<Integer> estadoInicial = new ArrayList<>();
        estadoInicial.add(s);
        ArrayList<Transition> transiciones = this.AFND.getDelta(s);
        if (transiciones != null) {
            for (Transition transicion : transiciones) {
                if (transicion.getL() == '_') {
                    if (!estadoInicial.contains(transicion.getTarget())) {
                        estadoInicial.add(transicion.getTarget());
                        ArrayList<Integer> posible = clausuraEpsilon(transicion.getTarget());
                        for (int i = 0; i < posible.size(); i++) {
                            if (!estadoInicial.contains(posible.get(i))) {
                                estadoInicial.add(posible.get(i));
                            }
                        }
                    }
                }
            }
        }

        return estadoInicial;
    }

    private HashMap<Integer, ArrayList<Integer>> conversion(ArrayList<Integer> estado, HashMap<Integer, ArrayList<Integer>> estadosAFD) {
        HashMap<Character, ArrayList<Integer>> resultados = new HashMap<>();
        ArrayList<Character> letras = obtenerSigma();
        for (char letra : letras) {
            ArrayList<Integer> nuevoEstado = new ArrayList<>();
            for (Integer e : estado) {
                if (this.AFND.getDelta(e) != null) {
                    ArrayList<Transition> transiciones = this.AFND.getDelta(e);
                    for (Transition t : transiciones) {
                        if (t.getL() == letra) {
                            nuevoEstado.add(t.getTarget());
                            ArrayList<Integer> estadosep = new ArrayList<>();
                            estadosep = clausuraEpsilon(t.getTarget());
                            nuevoEstado = comprobar(nuevoEstado, estadosep);
                        }
                    }
                }

            }
            //Collections.sort(nuevoEstado);
            resultados.put(letra, nuevoEstado);
        }
        Iterator it1 = resultados.keySet().iterator();
        while (it1.hasNext()) {
            Character key1 = (Character) it1.next();
            ArrayList<Integer> nuevoEstado = resultados.get(key1);
            if (!compruebaestado(nuevoEstado, estadosAFD)) {
                estadosAFD.put(this.numEstado++, nuevoEstado);
                conversion(nuevoEstado, estadosAFD);
            }
        }
        crearTransicionesAFD(estado, estadosAFD, resultados);
        return estadosAFD;
    }

    private void crearTransicionesAFD(ArrayList<Integer> estado, HashMap<Integer, ArrayList<Integer>> estadosAFD, HashMap<Character, ArrayList<Integer>> resultados) {
        Iterator it = estadosAFD.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            if (equalLists(estado, estadosAFD.get(key))) {
                transicionesEstado(resultados, estadosAFD, key);
            }
        }
    }

    private void transicionesEstado(HashMap<Character, ArrayList<Integer>> resultados, HashMap<Integer, ArrayList<Integer>> estadosAFD, Integer key2) {
        ArrayList<Transition> transiciones = new ArrayList<>();
        Iterator it = resultados.keySet().iterator();
        while (it.hasNext()) {
            Character key = (Character) it.next();
            Iterator it1 = estadosAFD.keySet().iterator();
            while (it1.hasNext()) {
                Integer key1 = (Integer) it1.next();
                if (equalLists(resultados.get(key), estadosAFD.get(key1))) {
                    transiciones.add(new Transition(key, key1));
                }
            }
        }
        this.delta.put(key2, transiciones);

    }

    private ArrayList<Integer> comprobar(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        for (Integer i : l2) {
            if (!l1.contains(i)) {
                l1.add(i);
            }
        }
        return l1;
    }

    private boolean compruebaestado(ArrayList<Integer> estado, HashMap<Integer, ArrayList<Integer>> estadosAFD) {
        Iterator it = estadosAFD.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            ArrayList<Integer> l1 = estadosAFD.get(key);
            if (equalLists(l1, estado)) {
                return true;
            }
        }

        return false;
    }

    public boolean equalLists(ArrayList<Integer> a, ArrayList<Integer> b) {
        // comprobar que tienen el mismo tamaño y que no son nulos
        if ((a.size() != b.size()) || (a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        if (a == null && b == null) {
            return true;
        }

        // ordenar las ArrayList y comprobar que son iguales          
        Collections.sort(a);

        Collections.sort(b);

        return a.equals(b);
    }
}

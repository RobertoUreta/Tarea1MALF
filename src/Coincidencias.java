
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
class Coincidencias {
    
    /**
     * Metodo que busca las coincidencias de una ER en una cadena
     * @param afd - Automata finito determinista (con sigma* por delante).
     * @param afdSinEstrella - Automata finito determinista (sin sigma* por delante).
     * @param cadena - String en el que se buscan coincidencias.
     */
    static void buscarCoincidencias(AFD afd, AFD afdSinEstrella, String cadena) {

        ArrayList<Integer> fines = Coincidencias.buscarFinesDeCalce(afd, cadena);
        ArrayList<Integer> comienzo = Coincidencias.buscarInicioCalces(afdSinEstrella, cadena, fines);
        Coincidencias.imprimir(comienzo, fines);
    }
    
    /**
     * Metodo que imprime los calces de la ER en la cadena
     * @param comienzos - ArrayList que contiene los comienzos de los calces de la ER en la cadena.
     * @param fines - ArrayList que contiene los finales de los calces de la ER en la cadena.
     */
    private static void imprimir(ArrayList<Integer> comienzos, ArrayList<Integer> fines) {
        if (comienzos.size() == fines.size()) {
            int n = comienzos.size();
            for (int i = n - 1; i >= 0; i--) {
                System.out.println(comienzos.get(i) + " " + fines.get(i));

            }
        } else {
            System.out.println("ERROR");
        }

    }
    
    /**
     * Metodo que busca el final de los calces de una ER en una cadena de texto.
     * @param afd - Automata finito determinista de la ER (con sigma* por delante).
     * @param cadena - String analizado.
     * @return - ArrayList de enteros que representan la posicion de la cadena en la que se produce el final del calce.
     */
    private static ArrayList<Integer> buscarFinesDeCalce(AFD afd, String cadena) {
        ArrayList<Integer> finCalces = new ArrayList<>();
        Integer inicio = afd.getS();
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!afd.sigma.contains(c)) {
                c = '$';
            }
            for (Transition tr : afd.delta.get(inicio)) {
                if (tr.l.equals(c)) {
                    inicio = tr.getTarget();

                }
            }

            if (afd.F.contains(inicio)) {
                finCalces.add(i);

            }

        }

        return finCalces;
    }
    
    /**
     * Método que permite buscar los inicios de los calces de una ER en la cadena usando un automata finito determinista de la ER.
     * @param afdSinEstrella
     * @param cadena
     * @param fines - ArrayList que contiene las posiciones donde se encuentran los fines de calce.
     * @return ArrayList de enteros, los cuales representan la posicion del inicio de los calces en la cadena.
     */
    private static ArrayList<Integer> buscarInicioCalces(AFD afdSinEstrella, String cadena, ArrayList<Integer> fines) {
        ArrayList<Integer> inicioCalces = new ArrayList<>();

        for (int k = fines.size() - 1; k >= 0; k--) {
            Integer f = fines.get(k);
            fines.remove(f);
            Integer ini = -1;
            int i;
            int calce = -1;
            for (i = f; i >= 0; i--) {

                String cadena2 = cadena.substring(i, f + 1);


                ini = recorreAutomata(afdSinEstrella, cadena2);
                if (ini != -1) {
                    calce = i;
  
                }

            }
            
            if (calce != -1) {
                if (inicioCalces.size()> 0 && calce == inicioCalces.get(inicioCalces.size() - 1)) {
                    //fines.remove(fines.size() - 1);
                    //fines.add(f);
                } else {
                    inicioCalces.add(calce);
                    fines.add(f);
                }

            }

        }

        return inicioCalces;
    }
    
    /**
     * Metodo auxiliar que permite ejecutar el automata finito determinista con la expresion subCadena
     * @param afdSinEstrella - Automata finito determinista de la ER (sin sigma* por delante).
     * @param subCadena - SubString de la cadena analizada.
     * @return 
     */
    private static Integer recorreAutomata(AFD afdSinEstrella, String subCadena) {
        Integer inicio = afdSinEstrella.getS();
        for (int i = 0; i < subCadena.length(); i++) {
            char c = subCadena.charAt(i);
            if (afdSinEstrella.sigma.contains(c)) {
                for (Transition tr : afdSinEstrella.delta.get(inicio)) {
                    if (tr.l.equals(c)) {
                        inicio = tr.getTarget();

                    }
                }
            } else {
                return -1;
            }
        }

        if (afdSinEstrella.F.contains(inicio)) {
            return inicio;
        } else {
            return -1;
        }

    }


}

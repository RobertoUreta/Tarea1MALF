
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fernanda
 */
public class AFND {

    Stack<Thomson> stack;
    ER ER;

    public AFND(ER ER, boolean sigmaEstrella) {

        this.ER = ER;
        this.stack = new Stack<Thomson>();
        this.transformar();

        if (sigmaEstrella) {
            Thomson item = this.stack.pop();
            item.agregarRizo();
            this.stack.push(item);
        }

    }

    private void transformar() {
        int s = 0;
        for (int i = 0; i < ER.lengthER(); i++) {

            //System.out.println(ER.charAtER(i));
            char c = ER.charAtER(i);
            if (c == '~') {
                //System.out.println("//Thomson de phi");
                Thomson thPhi = new Thomson(s);
                //thPhi.imprimirThc();
                this.stack.push(thPhi);
                s = thPhi.ultimoEstado() + 1;
            } else if (this.ER.esDelAlfabetoDeER(c)) {
                //Thomson de un caracter
                //System.out.println("Thomson de un caracter" + " " + c);

                Thomson th = new Thomson(c, s);
                s = th.ultimoEstado() + 1;
                this.stack.push(th);
                //th.imprimirThc();

            } else {
                switch (c) {
                    case '.':
                        //Thomson de concatenacion
                        //System.out.println("Thomson de concatenacion");
                        Thomson pop = stack.pop();
                        Thomson pop0 = stack.pop();
                        //this.renombrarEstados(pop, pop0);
                        Thomson thConc = new Thomson(pop, pop0, c);
                        this.stack.push(thConc);
                        //thConc.imprimirThc();
                        s = thConc.ultimoEstado() + 1;

                        break;
                    case '|':
                        //Thomson de or
                        //System.out.println("//Thomson de or");
                        Thomson pop1 = stack.pop();
                        Thomson pop2 = stack.pop();
                        Thomson thOr = new Thomson(pop1, pop2, c);
                        //thOr.imprimirThc();
                        this.stack.push(thOr);
                        s = thOr.ultimoEstado() + 1;
                        break;

                    case '*':
                        //Thomson de *
                        //System.out.println("//Thomson de *");
                        Thomson thEst = new Thomson(stack.pop());
                        //thEst.imprimirThc();
                        this.stack.push(thEst);
                        s = thEst.ultimoEstado() + 1;
                        break;

                }

            }

        }

    }

    public Thomson obtenerAFND() {
        return this.stack.peek();
    }

}

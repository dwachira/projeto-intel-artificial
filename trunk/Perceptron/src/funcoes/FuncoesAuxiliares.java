/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package funcoes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import perceptron.Perceptron;

/**
 *
 * @author Larissa
 */
public class FuncoesAuxiliares {

    //classe ainda está sem uso...mas a ideia é colocar as comuns nesse pacote
    // mas antes precisa mudar algumas coisas...como tirar as variáveis globais. =)


    Perceptron per = new Perceptron();

    public void lerArquivo(String tipo, String arquivo){

        int count = 0;
        try {
        BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;
            while (in.ready()) {
                str = in.readLine();

                if(tipo.equals("treinoP"))
                    per.montaMatrizTreinamento(str, count);
                else if(tipo.equals("testeP"))
                     per.montaMatrizTeste(str, count);

                count++;

            }
            in.close();

        } catch (IOException e) {
        }
    }
}

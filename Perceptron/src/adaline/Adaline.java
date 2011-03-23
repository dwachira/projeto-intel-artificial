/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package adaline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Larissa
 */
public class Adaline {

    double taxaAprendizagem = 0.0025;
    double[] w = new double[5]; //vetor de pesos
    double y = 0; //saída do adaline
    double[][] x = new double[35][6];  //matriz com valores de treinamento e saída desejado
    //double[][] x2 = new double[10][4]; //matriz com valores de teste
    Random rand = new Random();
    double bias = -1.0;
    double erro = 0.000001;
    double EQM_ant;
    double EQM_atual;
    double EQM;
    double INF;

     public void lerArquivo(String tipo, String arquivo){
        
        int count = 0;
        try {
        BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;
            while (in.ready()) {
                str = in.readLine();

                if(tipo.equals("treino"))
                    montaMatrizTreinamento(str, count);
                else
                     montaMatrizTeste(str, count);


                count++;

            }
            in.close();

        } catch (IOException e) {
        }
    }

     public void montaMatrizTreinamento(String linha, int c){

       String[] partes = new String[5];
       double num;
           partes = linha.split(" ");

           for(int j = 0; j<6; j++){
                num = Double.parseDouble(partes[j]);
                x[c][j] = num;
            }
    }

    public void montaMatrizTeste(String linha, int c){

      /* String[] partes = new String[4];
       double num;
           partes = linha.split(" ");

           for(int j = 0; j<4; j++){
                num = Double.parseDouble(partes[j]);
                x2[c][j] = num;
            }       */
    }

     public void inicializarVetorPesos(){
        System.out.println("\n Pesos iniciais (randomicos)");
        for (int i = 0; i < 5; i++) {//w[0], w[1], w[2], w[3]
            w[i] = 1*(double)Math.random();

	}
         System.out.println(w[0] + "\n" + w[1] + "\n" + w[2] + "\n" + w[3]);
    }

     public void treinamento(){

        //obterConjTreino();
        int epoca = 0;
        EQM_ant = INF;
        EQM_atual = EQM;
        while(  (EQM_atual = EQM_ant) > erro ){

            EQM_ant = EQM_atual;
             for (int i = 0; i < 30; i++) {
                // A saída recebe o resultado da rede que no caso é -1 ou 1
		//y = executar(x[i][0], x[i][1], x[i][2], x[i][3]);
               // normalizarPeso(i, y);


            }
            epoca++;
            EQM_atual = EQM;
        }
        System.out.println("\n Epocas: " + epoca);

        System.out.println("\n Vetor de pesos final: \n w[0] = " + w[0] + "\n w[1] = " + w[1]+ "\n w[2] = " + w[2]+ "\n w[3] = " + w[3] + "\n w[4] = " + w[4] +"\n\n");
    }

     public static void main(String[] args) {

     }
}

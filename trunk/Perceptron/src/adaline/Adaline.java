/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package adaline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFileChooser;

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
    double INF = 1000000000;
    int linhasDados = 0;

     public void lerArquivo(String tipo, String arquivo){
        
        int count = 0;
        try {
        BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;
            while (in.ready()) {
                str = in.readLine();

                if(tipo.equals("TreinoA"))
                    montaMatrizTreinamento(str, count);
                else
                     montaMatrizTeste(str, count);


                count++;

            }
            in.close();
            linhasDados = count;

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
                System.out.println("matriz ["+c+"]["+j+"] " + x[c][j]);
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
        EQM_atual = calc_EQM();
         System.out.println("EQM_atual " + EQM_atual + " EQM_ant " + EQM_ant);
        while(  Math.abs(EQM_atual - EQM_ant) > erro ){
            System.out.println("while");
            EQM_ant = EQM_atual;
             for (int i = 0; i < linhasDados; i++) {
                 double u = executar(x[i][0], x[i][1], x[i][2], x[i][3], x[i][4]);
                 atualizarPeso(i,u);

            }
            epoca++;
            EQM_atual = calc_EQM();
        }
        System.out.println("\n Epocas: " + epoca);

        System.out.println("\n Vetor de pesos final: \n w[0] = " + w[0] + "\n w[1] = " + w[1]+ "\n w[2] = " + w[2]+ "\n w[3] = " + w[3] + "\n w[4] = " + w[4] +"\n\n");
    }

     public double executar(double x0, double x1, double x2, double x3, double x4) {
		y = (x0 * w[0]) + (x1 * w[1]) + (x2 * w[2]) + (x3 * w[3] + (x4 * w[4]) );
                return y;
     }

     public void atualizarPeso(int i, double saida) {

        w[0] = w[0] + taxaAprendizagem *(x[i][4] - saida)* x[i][0];
        w[1] = w[1] + taxaAprendizagem *(x[i][4] - saida)* x[i][1];
        w[2] = w[2] + taxaAprendizagem *(x[i][4] - saida)* x[i][2];
        w[3] = w[3] + taxaAprendizagem *(x[i][4] - saida)* x[i][3];
        w[4] = w[4] + taxaAprendizagem *(x[i][4] - saida)* x[i][4];
       // System.out.println("normalizado " + w[0] + " "+ w[1] + " " + w[2] + " "+ w[3]);
    }

     public double calc_EQM(){

         double soma = 0;
         double u;

         for(int i = 0; i<linhasDados; i++){ //somatório
            u = executar(x[i][0], x[i][1], x[i][2], x[i][3], x[i][4]);
            soma = soma + Math.pow((x[i][5] - u),2);
         }
         EQM = soma/linhasDados;
         return EQM;
     }
String file;
      public String lerArquivoAbreJanela(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.CANCEL_OPTION){

        }
        else{
            file = fileChooser.getSelectedFile().getPath();
            System.out.println("file " + file);
            return file;
        }
        return null;
    }
     public static void main(String[] args) {

         Adaline ad = new Adaline();

         String f = ad.lerArquivoAbreJanela();
         ad.lerArquivo("TreinoA", f);
         ad.inicializarVetorPesos();
         ad.treinamento();

     }
}

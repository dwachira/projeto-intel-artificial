package perceptron;





import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Larissa
 */


public class Perceptron {

    double taxaAprendizagem = 0.01;
    double[] w = new double[4];
    double y = 0;
    double[][] x = new double[30][5];
    Random rand = new Random();
    double bias = -1.0;//tÃ¡ certo!


    public void lerArquivo(){
        //System.out.println("Matriz original");
        int count = 0;
        try {
        BufferedReader in = new BufferedReader(new FileReader("dados.txt"));
            String str;
            while (in.ready()) {
                str = in.readLine();

                montaMatriz(str, count);
                count++;

            }
            in.close();

        } catch (IOException e) {
        }
    }

    public void montaMatriz(String linha, int c){

       String[] partes = new String[5];
       double num;

       //for(int i = 0; i<30; i++){
           partes = linha.split(" ");

           for(int j = 0; j<5; j++){
                num = Double.parseDouble(partes[j]);
                x[c][j] = num;
         //       System.out.println("matriz[" +c + "][" +j+ "] = " + x[c][j]);
            }
        //}
    }

    public void normalizarMatrizX(){

        //encontra valor minimo e maximo
        double max = x[0][1];
        double min = x[0][1];
        double num;
        for(int i=0; i<30; i++){
            for(int j=1; j<4; j++){
               num = x[i][j];
               if(num <= min)
                    min = num;
               else if(num > max)
                    max = num;
            }
        }
       // System.out.println("\n min = " + min + " max = " + max);

       // System.out.println("\n Matriz normalizada");
        //normalizar: (x[i][j] - minimo do arquivo)/maximo - minimo
        double val=0;
        for(int i=0; i<30; i++){
            for(int j=0; j<4; j++){
                val = x[i][j];
                if (val < 0) {
                    x[i][j] = (x[i][j] - min) / (max - min);
                    x[i][j] = x[i][j] * -1;
                } else {
                    x[i][j] = (x[i][j] - min) / (max - min);
                }
         //       System.out.println("matrizN[" +i + "][" +j+ "] = " + x[i][j]);
            }
         }
    }

    public void obterConjTreino(){
       lerArquivo();//aqui ele ler o arquivo e monta a matriz x

    }

    public void inicializarVetorPesos(){
       // System.out.println("\n Pesos iniciais (randomicos)");
        for (int i = 0; i < 4; i++) {//w[0], w[1], w[2], w[3]
            w[i] = 1*(double)Math.random();
           // System.out.println(w[i]);
	}
    }

    public void treinamento(){

        //obterConjTreino();
        int epoca = 0;
        boolean erro;
        do{
             erro = false;
             for (int i = 0; i < 10; i++) {
                // A saída recebe o resultado da rede que no caso é -1 ou 1
		y = executar(x[i][0], x[i][1], x[i][2], x[i][3]);

                 System.out.println("valor do i " + i);
                if (y != x[i][4]) {
                   normalizarPeso(i, y);
                   erro = true;
                }
            }
            epoca++;
        }while (erro == true);

        System.out.println("\n Vetor de pesos final: \n w[0] = " + w[0] + "\n w[1] = " + w[1]+ "\n w[2] = " + w[2]+ "\n w[3] = " + w[3]);
    }

    public double executar(double x0, double x1, double x2, double x3) {
		y = (x0 * w[0]) + (x1 * w[1]) + (x2 * w[2]) + (x3 * w[3]);
                System.out.println("valor do y "+ y);
        // Funcao de Ativação: função sinal
        if (y >= 0) {
            return 1;
        }
        return -1;
    }

    public void normalizarPeso(int i, double saida) {

        w[0] = w[0] + taxaAprendizagem *(x[i][4] - saida)* x[i][0];
        w[1] = w[1] + taxaAprendizagem *(x[i][4] - saida)* x[i][1];
        w[2] = w[2] + taxaAprendizagem *(x[i][4] - saida)* x[i][2];
        w[3] = w[3] + taxaAprendizagem *(x[i][4] - saida)* x[i][3];
        System.out.println("normalizado " + w[0] + " "+ w[1] + " " + w[2] + " "+ w[3]);
    }

 public static void main(String[] args) {
        // TODO code application logic here

    // inicializarVetorPesos();
     Perceptron p = new Perceptron();
     p.lerArquivo();
     p.normalizarMatrizX();
     p.inicializarVetorPesos();
     p.treinamento();

    }


}

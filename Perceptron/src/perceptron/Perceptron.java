package perceptron;





import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFileChooser;

/**
 *
 * @author Larissa
 */


public class Perceptron {

    double taxaAprendizagem = 0.001;
    double[] w = new double[4]; //vetor de pesos
    double y = 0; //saída do Perceptron
    double[][] x = new double[30][5];  //matriz com valores de treinamento e saída desejado
    double[][] x2 = new double[10][4]; //matriz com valores de teste
    Random rand = new Random();
    double bias = -1.0;
    String file;
    int linhasDados = 0;


    public void lerArquivoAbreJanela(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.CANCEL_OPTION){

        }
        else{
            file = fileChooser.getSelectedFile().getPath();
            System.out.println("file " + file);
        }
    }

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
            linhasDados = count;
        } catch (IOException e) {
        }
    }

   /*public void lerArquivoTeste(){
        
        int count = 0;
        try {
        BufferedReader in = new BufferedReader(new FileReader("teste.txt"));
            String str;
            while (in.ready()) {
                str = in.readLine();

                montaMatrizTeste(str, count);
                count++;

            }
            in.close();

        } catch (IOException e) {
        }
    }*/

    public void montaMatrizTreinamento(String linha, int c){

       String[] partes = new String[5];
       double num;      
           partes = linha.split(" ");
         
           for(int j = 0; j<5; j++){
                num = Double.parseDouble(partes[j]);
                x[c][j] = num;
                System.out.println("matriz["+ c+ "]["+j+ "] " + x[c][j]);
            }
          
    }

    public void montaMatrizTeste(String linha, int c){

       String[] partes = new String[4];
       double num;      
           partes = linha.split(" ");
         
           for(int j = 0; j<4; j++){
                num = Double.parseDouble(partes[j]);
                x2[c][j] = num;        
            }
        
           




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
        double val=0;
        for(int i=0; i<30; i++){
            for(int j=0; j<4; j++){
                val = x[i][j];
                    x[i][j] = (x[i][j] - min) / (max - min);                  
            }
         }
    }

    public void normalizarMatrizTeste(){

        //encontra valor minimo e maximo
        double max = x2[0][1];
        double min = x2[0][1];
        double num;
        for(int i=0; i<10; i++){
            for(int j=0; j<3; j++){
               num = x2[i][j];
               if(num <= min)
                    min = num;
               else if(num > max)
                    max = num;
            }
        }
        System.out.println("min "+ min + " max " + max);
        double val=0;
        for(int i=0; i<10; i++){
            for(int j=0; j<4; j++){
                val = x2[i][j];
                    x2[i][j] = (x2[i][j] - min) / (max - min);
                    System.out.println("matriz teste: " + x2[i][j]);
            }
         }
    }

    public void inicializarVetorPesos(){
        System.out.println("\n Pesos iniciais (randomicos)");
        for (int i = 0; i < 4; i++) {//w[0], w[1], w[2], w[3]
            w[i] = 1*(double)Math.random();
          
	}
         System.out.println(w[0] + "\n" + w[1] + "\n" + w[2] + "\n" + w[3]);
    }

    public void treinamento(){

        //obterConjTreino();
        int epoca = 0;
        boolean erro;
        //linhasDados = 30;
        do{
             erro = false;
             for (int i = 0; i < linhasDados; i++) {
                // A saída recebe o resultado da rede que no caso é -1 ou 1
		y = executar(x[i][0], x[i][1], x[i][2], x[i][3]);

                // System.out.println("valor do i " + i);
                if (y != x[i][4]) {
                   atualizarPeso(i, y);
                   erro = true;
                }
            }
            epoca++;
        }while (erro == true);
        System.out.println("\n Epocas: " + epoca);

        System.out.println("\n Vetor de pesos final: \n w[0] = " + w[0] + "\n w[1] = " + w[1]+ "\n w[2] = " + w[2]+ "\n w[3] = " + w[3]+"\n\n");
    }

    public double executar(double x0, double x1, double x2, double x3) {
		y = (x0 * w[0]) + (x1 * w[1]) + (x2 * w[2]) + (x3 * w[3]);
               // System.out.println("valor do y "+ y);
        // Funcao de Ativação: função sinal
        if (y >= 0) {
            return 1;
        }
        return -1;
    }

    public void testar(){

        for (int i = 0; i < 10; i++) {

            y = executar(x2[i][0], x2[i][1], x2[i][2], x2[i][3]);

            if (y==(-1)) {
                System.out.println("Padrão "+(i+1)+" Pertence à classe C1! -> y = -1");
            } else {
                System.out.println("Padrão "+(i+1)+" Pertence à classe C2! -> y = 1");
            }

        }
    } //fim do método testar

    public void atualizarPeso(int i, double saida) {

        w[0] = w[0] + taxaAprendizagem *(x[i][4] - saida)* x[i][0];
        w[1] = w[1] + taxaAprendizagem *(x[i][4] - saida)* x[i][1];
        w[2] = w[2] + taxaAprendizagem *(x[i][4] - saida)* x[i][2];
        w[3] = w[3] + taxaAprendizagem *(x[i][4] - saida)* x[i][3];
       // System.out.println("normalizado " + w[0] + " "+ w[1] + " " + w[2] + " "+ w[3]);
    }

 public static void main(String[] args) {

     Perceptron p = new Perceptron();
    // p.lerArquivoAbreJanela();
 
//-------------Treino----------------------------
     p.lerArquivo("treino", "dados.txt");
    // p.normalizarMatrizX();   
     p.inicializarVetorPesos();
     p.treinamento();
//-------------Teste----------------------------
     p.lerArquivo("teste", "teste.txt");
    // p.lerArquivoTeste();
    // p.normalizarMatrizTeste();
     p.testar();

    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dijkstra {
    //variable a utilizar el varias funciones
    public static int max=999999;
    
    //funcion principal
    public static void dijkstra(int[][] grafo,int start){
        int cant = grafo.length;
        //almacena la distancias minimas para llegar a cada nodo
        int[] distancia=new int[cant];
        //validar si el nodo ya esta visitado
        boolean[] visitado=new boolean[cant];
        //almacena el nodo padre de donde se calculo la distancia minima
        int[] padre = new int[cant];
        
        //inicializacion de variables
        for(int i=0;i<cant;i++){
            distancia[i] = max;
            padre[i] = -1;
        }
        
        //el nodo de inicio su distancia es 0
        distancia[start] = 0;
        /*Tiene que encontrar las distancias minimas de todos los nodos, 
        excepto el nodo origen*/
        for(int i=0;i<cant-1;i++){
            /*Halla el nodo con arista de menos peso y que no este visitado*/
            int n = nodomin(distancia,visitado);
            visitado[n] = true;
            //determina la menor distancia a cada nodo
            for(int k=0;k<cant;k++){
                int suma = distancia[n] + grafo[n][k];
                if(!visitado[k] && grafo[n][k]!=0 && distancia[n]!=max 
                        && suma<distancia[k]){
                    distancia[k] = suma;
                    padre[k] = n;
                }
            }
        }
        imprimir(distancia,padre);
    }
    
    public static int nodomin(int[] distancia,boolean[] visitado){
        int min = max;
        int nodomin=-1;
        int cant = distancia.length;
        
        for(int i=0;i<cant;i++){
            if(!visitado[i] && distancia[i]<min){
                min = distancia[i];
                nodomin = i;
            }
        }
        return nodomin;
    }
    
    public static void imprimir(int[] distancia,int[] padre){
        System.out.println("Nodo\tDistancia\tCamino");
        System.out.println("==================================================");
        for(int i=0;i<distancia.length;i++){
            System.out.print((i+1)+"\t"+distancia[i]+"\t\t");
            //imprimir el camino que sigue desde el nodo padre hasta ese nodo
            imprimirCamino(padre,i);
            System.out.println("");
        }
    }
    
    public static void imprimirCamino(int[] padre,int n){
        if(padre[n]==-1){
            System.out.print(n+1);
            return;
        }
        imprimirCamino(padre,padre[n]);
        System.out.print("->" + (n+1));
    }

    public static int[][] crearMatriz(int len) {
        Scanner sc = new Scanner(System.in);
        int[][] matriz = new int[len][len];
        int op =1;
        do{
        System.out.println("El grafo es dirigido?: Si(1)  No(0)");
        op = sc.nextInt();}while(!(op==1||op==0));
        if(op==1){
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (i != j) {
                        System.out.print("Peso de la arista entre el nodo " + (i+1) + " y el nodo " + (j+1) + ": ");
                        matriz[i][j] = sc.nextInt();
                    } 
                    else {
                        // No se permite un nodo con peso propio en Dijkstra
                        matriz[i][j] = 0;
                    }
                }
            }
        }
        else{
            for (int i = 0; i < len; i++) {
                for (int j = i; j < len; j++) {
                    if (i != j) {
                        System.out.print("Peso de la arista entre el nodo " + (i+1) + " y el nodo " + (j+1) + ": ");
                        matriz[i][j] = sc.nextInt();
                        matriz[j][i] = matriz[i][j];
                    } 
                    else {
                        // No se permite un nodo con peso propio en Dijkstra
                        matriz[i][j] = 0;
                    }
                }
            }
        }
        return matriz;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("¿Desea ingresar un nuevo grafo? (Sí: 1, No: 0): ");
        
        int opcion = sc.nextInt();
        int[][] grafo;
        if (opcion == 1) {
            System.out.print("Ingrese la cantidad de nodos del grafo: ");
            int cantNodos = sc.nextInt();
            grafo = crearMatriz(cantNodos);
        } 
		else {
            grafo = new int[][] {{0, 1, 2, 0, 0, 0},
                      {1, 0, 1, 5, 0, 0},
                      {2, 1, 0, 8, 10, 0},
                      {0, 5, 8, 0, 2, 6},
                      {0, 0, 10, 2, 0, 2},
                      {0, 0, 0, 6, 2, 0}};
        }

       /* int[][] grafo = {{0, 1, 2, 0, 0, 0},
                      {1, 0, 1, 5, 0, 0},
                      {2, 1, 0, 8, 10, 0},
                      {0, 5, 8, 0, 2, 6},
                      {0, 0, 10, 2, 0, 2},
                      {0, 0, 0, 6, 2, 0}};
        
        int[][] grafo2 = {{0, 1, 25, 13, 0, 0, 2, 0, 0, 0, 0, 0},
                          {1, 0, 2, 0, 5, 0, 0, 0, 0, 0, 0, 0},
                          {25, 2, 0, 0, 30, 11, 0, 0, 0, 0, 0, 0},
                          {13, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0},
                          {0, 5, 30, 0, 0, 4, 0, 14, 15, 0, 0, 0},
                          {0, 0, 11, 0, 4, 0, 17, 0, 9, 0, 0, 0},
                          {2, 0, 0, 12, 0, 17, 0, 0, 0, 8, 0, 0},
                          {0, 0, 0, 0, 14, 0, 0, 0, 3, 0, 6, 5},
                          {0, 0, 0, 0, 15, 9, 0, 3, 0, 8, 0, 0},
                          {0, 0, 0, 0, 0, 0, 8, 0, 8, 0, 7, 6},
                          {0, 0, 0, 0, 0, 0, 0, 6, 0, 7, 0, 3},
                          {0, 0, 0, 0, 0, 0, 0, 5, 0, 6, 3, 0}}; */
        
        System.out.println("Ingrese el nodo de Inicio: ");
        int nodoIn = sc.nextInt()-1;

        long tiempoInicio = System.currentTimeMillis();
        dijkstra(grafo,nodoIn);
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        System.out.println("\n\nTiempo de ejecución: " + tiempoTotal + " milisegundos");

        try (FileWriter writer = new FileWriter("matriz.csv")) {
            for (int[] fila : grafo) {
                for (int valor : fila) {
                    writer.write(Integer.toString(valor));
                    writer.write(",");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Ruta al ejecutable de Python y script Python
            
            // Construye el proceso
            ProcessBuilder processBuilder = new ProcessBuilder("python", "grafo.py");

            // Inicia el proceso
            Process process = processBuilder.start();

            // Espera a que el proceso de Python termine
            int exitCode = process.waitFor();

            // Imprime el código de salida del proceso de Python (0 indica éxito)
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

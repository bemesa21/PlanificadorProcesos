package planificadorProcesos;

import java.util.Scanner;

/**
 *
 * @author bere
 */
/*
Clase que define los metodos de los diferentes menus de la aplicacion.
*/
public class Menu{

	//Metodo del menu de opciones principal.
	public static int menuPrincipal(){
		Scanner scan = new Scanner(System.in);
		int opcion = 0;
		System.out.printf("\nPlanificador de procesos.\n\n");
		System.out.println("Seleccione una opcion:");
		System.out.println("1)	Iniciar Prioridad Apropiativo.");
		System.out.println("2)	Iniciar Round Robin.");
		try{
			opcion = scan.nextInt();
			scan.nextLine();
		}catch(Exception e){
			opcion = -1;
		}
		return opcion;
	}
	
	//Metodo que solicita al usuario los datos de un nuevo proceso y regresa un proceso.
	public static Proceso creaProceso(){
		int idProceso;
		String nombreProceso;
		int tamanho;
		int tiempoDeEjecucion;
		int prioridad;
                int tiempoLlegada;
		Scanner scan = new Scanner(System.in);

		System.out.printf("\nRegistro del proceso.\nIngresa el id del proceso:\n");
		idProceso = scan.nextInt();
		scan.nextLine();
		System.out.println("Nombre del proceso: ");
		nombreProceso = scan.nextLine();
		System.out.println("Tamano del proceso (bytes):");
		tamanho = scan.nextInt();
		scan.nextLine();
                System.out.println("Prioridad:");
		prioridad = scan.nextInt();
		scan.nextLine();
		System.out.println("Tiempo de llegada del proceso (milisegundos):");
		tiempoLlegada = scan.nextInt();
		scan.nextLine();
                System.out.println("Tiempo de ejecución del proceso (milisegundos):");
		tiempoDeEjecucion = scan.nextInt();
		scan.nextLine();
		System.out.println("Prioridad:");
		prioridad = scan.nextInt();
		scan.nextLine();
	        return new Proceso(idProceso, tamanho, tiempoDeEjecucion, prioridad, tiempoLlegada);
		//return new Proceso(idProceso, nombreProceso, tamanho, tiempoDeEjecucion, prioridad, tiempoLlegada);
	}

}
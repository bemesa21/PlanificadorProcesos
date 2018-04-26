/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorProcesos;

import java.util.Scanner;

/**
 *
 * @author bere
 */
public class Principal{
	public static void main(String[] args){
		int opcion = 1;

		while(true){
			opcion = Menu.menuPrincipal();
			switch(opcion){
				case 1:
                                    PlanificadorProcesos.prioridadesApropiativo();
				break;
				case 2:
                                    PlanificadorProcesos.roundRobinLlegada();
				break;
				default:
					System.out.println("Opcion no valida!");
			}
		}
	}
}
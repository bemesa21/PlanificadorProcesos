/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorProcesos;

/**
 *
 * @author bere
 */
/*
Clase que define un nodo, con el cual almacenaremos procesos asi como 
referencia al siguiente nodo.
*/
public class Nodo{
	//Almacena un proceso.
	public Proceso proceso;
	//Almacena un apuntador al siguiente nodo.
	public Nodo siguiente;
        public Nodo anterior;

	//Constructor
	public Nodo(Proceso p){
		proceso = p;
	}
}

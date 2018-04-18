
package planificadorProcesos;
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

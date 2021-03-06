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
//Clase que define una cola de nodos que almacenaran procesos.
public class Cola{
	//Definimos el inicio de la cola y el final de la cola.
	protected Nodo head, tail;
	//Sera la variable que nos ayudara a llevar la cuenta de procesos en cola.
	protected int numProcesos = 0;

	//Constructor.
	public Cola(){
		head = null;
		tail = null;
	}

	//Metodo que comprueba que la cola este vacia.
	public boolean vacia(){
		return head == null ? true : false;
	}

    public Nodo getHead() {
        return head;
    }


	//Metodo que inserta un nuevo nodo a la cola
	public void insertarProceso(Proceso p){
		Nodo n = new Nodo(p);
		if (vacia()) {
			head = n;
			tail = n;
		}
		else{
			tail.siguiente = n;
                        n.anterior = tail; 
			tail = n;
		}
		numProcesos++;
	}

        
         public void insertarProcesoPorPrioridad(Proceso p){
            Nodo n = new Nodo(p);
            Nodo aux,aux2;
            //la cola esta vacia
            if(vacia()){
                head = tail = n;
            }
            //la cola contiene algo, mandamos a recorrer COla en busca de lugar
            else{
                //
                aux=recorreBuscandoLugar(n);
                if(aux !=  null){
                    if (aux == head){
                        head.anterior=n;
                        head=n;
                        head.siguiente=aux;
                    }else{
                       aux2=aux.anterior;
                       aux2.siguiente=n;
                       n.anterior=aux.anterior;
                       aux.anterior=n;
                       n.siguiente=aux;
                   }
                    
                }else{
                    n.anterior=tail;
                    tail=n;
                }
            } 
        }

       //retornar el nodo que tenga menor prioridad que n
        public Nodo recorreBuscandoLugar(Nodo n){
            //inicias en la cabeza
            Nodo aux=getHead();
            Nodo nodoAretornar = null;
           //mientras no llegues a despues de la cola
            while(aux != null ){
                //nodo que se quiere insertar tiene mayor prioridad al que recorremos
                if( n.proceso.getPrioridad() > aux.proceso.getPrioridad()){
                    //retornamos ese nodo, para que se inserte en su anterior
                    nodoAretornar = aux;
                    break;
                }
                        aux=aux.siguiente;
            }
            return nodoAretornar;
        }   
        
        

	//Metodo que elimina el ultimo nodo de la cola.
	public void sacarProceso(){
		if (vacia())
			System.out.printf("\nNo existen procesos en la cola.");
		else
			head = head.siguiente;
	}

	//Metodo que elimina un nodo de la cola sin importar su posicion.
	public void eliminarProceso(Proceso p){
		if (!vacia()) {
			if (head == tail && p.getId() == head.proceso.getId()) {
				head = tail = null;
			}else if(p.getId() == head.proceso.getId()){
				head = head.siguiente;
			}else{
				Nodo anterior, temp;
				anterior = head;
				temp = head.siguiente;
				while(temp != null && temp.proceso.getId() != p.getId()){
					anterior = anterior.siguiente;
					temp = temp.siguiente;
				}
				if (temp != null) {
					anterior.siguiente = temp.siguiente;
					if (temp == tail) {
						tail = anterior;
					}
				}
			}
			numProcesos--;
		}
	}

	//Metodo que lista la cola de nodos.
	public void listarCola(){
		Nodo n;
		if (!vacia()){
			n = head;
			while(n != null){
				n.proceso.datosProceso();
				n = n.siguiente;
			}
		}
	}

	//Metodo que lista los procesos con sus tiempos.
	public void listarColaConTiempos(){
		Nodo n;
		if (!vacia()){
			n = head;
			while(n != null){
				n.proceso.tiemposProceso();
				n = n.siguiente;
			}
		}
	}
        
        //Metodo de ordenamiento
        public void ordenarPorTiempoDeLlegada(){
            if (!vacia()) {
                int i, j;
                Proceso proc;
                Nodo p = tail, q;
                while(p != null){
                    q = tail;
                    while(q != null){
                        if(p.proceso.getTiempoLlegada() < q.proceso.getTiempoLlegada()){
                            proc = q.proceso;
                            q.proceso = p.proceso;
                            p.proceso = proc;
                        }
                        q = q.siguiente;
                    }
                    p = p.siguiente;
                }
            }
        }
        
          public void ordenarPorPrioridad(){
            if (!vacia()) {
                int i, j;
                Proceso proc;
                Nodo p = tail, q;
                while(p != null){
                    q = tail;
                    while(q != null){
                        if(p.proceso.getPrioridad()< q.proceso.getPrioridad()){
                            proc = q.proceso;
                            q.proceso = p.proceso;
                            p.proceso = proc;
                        }
                        q = q.siguiente;
                    }
                    p = p.siguiente;
                }
            }
        }
}

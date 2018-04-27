package planificadorProcesos;

import java.util.Scanner;

/**
 *
 * @author bere
 */
public class PlanificadorProcesos{
    /*
            colaMem, almacenara todos los procesos en "memoria".
            colaTiempos, almacenara todos los procesos que hayan terminado para despues
            calcular los tiempo de ejecucion, etc.
     */
    private static Cola colaMem; 
    private static Cola colaListos; 
    private static Cola colaTiempos;
    private static Cola colaProcesos; 
    private static int numProcesos;
    private static int memoriaTotal;

	//Variable que llevara el conteo del tiempo transcurrido de ejecucion.
	public static int tiempo = 0;
        
        public static void roundRobinLlegada(){
            /*
            memoriaTotal: almacena el tamaño de la memoria dada por el usuario.
            memoria: almacenara el mismo valor que memoriaTotal, pero modificaremos su valor
            durante las iteraciones.
            Q: almacenara el quantum para cada ejecucion del procesador.
            */
            int memoria, quantum;
            colaMem = new Cola();
            colaProcesos = new Cola();
            colaListos = new Cola();
            colaTiempos = new Cola();
            //Variable auxiliar.
            Nodo p;  
            Scanner teclado = new Scanner(System.in);
            System.out.println("¿Cuántos procesos simularemos?");
            numProcesos = teclado.nextInt();
            System.out.println("Ingresa el tamanho de la memoria en bytes:");
            memoriaTotal = teclado.nextInt();
            System.out.println("Tamaño del cuantum");
            quantum = teclado.nextInt();
            
            for (int i = 0; i < numProcesos; i++) {
                 colaProcesos.insertarProceso(Menu.creaProceso());
            }
            System.out.println("Cola de procesos en memoria, con una memoria de: "+ memoriaTotal);
            
            colaProcesos.ordenarPorTiempoDeLlegada();
            //colaProcesos.listarCola();
            
            
            //While que comprueba que la cola de procesos listos no este vacia.
            do{
                memoria = memoriaTotal;
                //Metodo que comprueba que procesos han llegado.
                comprobandoTiempoLlegada(colaListos, colaProcesos);
                /*
                El siguiente metodo solo se usara en caso de que la maestra nos de un problema
                en el que el primer proceso llegue en un tiempo diferente de 0.
                */
                if(colaListos.vacia()){
                    p = colaProcesos.head;
                    tiempo = p.proceso.getTiempoLlegada();
                    System.out.println("El tiempo actual es: "+tiempo);
                    colaListos.insertarProceso(p.proceso);
                    colaProcesos.eliminarProceso(p.proceso);
                }
                System.out.println("Cola de procesos por llegar:");
                colaProcesos.listarCola();
                System.out.println("La cola de procesos listos es:");
                colaListos.listarCola();
                p = colaListos.head;
                /*
                While que iterara sobre toda la cola de procesos listos y verificara cuales
                se puden agregar a la cola de procesos en memoria.
                */
                while(p != null){
                        //Compruebo que tengo suficiente memoria RAM.
                        if (memoria >= p.proceso.getTamanho()) {
                                colaMem.insertarProceso(p.proceso);
                                memoria-=p.proceso.getTamanho();
                                System.out.println("Se subio el proceso : "+p.proceso.getId()+" y restan "+memoria+" de memoria.");
                                colaListos.eliminarProceso(p.proceso);
                        }
                        else{
                            break;
                        }
                        p = p.siguiente;
                }

                System.out.printf("Cola de procesos listos para ejecucion:");
                colaMem.listarCola();

                //Comprobamos que se haya subido algun proceso a la cola de memoria.
                if (!colaMem.vacia()) {
                        System.out.println("No se pueden subir mas procesos!");
                        System.out.println("Ejecutando procesos en memoria...");
                        Nodo aux = colaMem.head;
                        //Iteramos sobre toda la cola.
                        while(!colaMem.vacia()){
                            if (aux.proceso.getTiempoLlegada() <= tiempo){
                                //Simluamos la ejecucion en CPU del proceso, con el quantum.
                                ejecutandoProceso(aux.proceso, quantum);
                                /*
                                Comprobamos si el proceso ya termino su ejecucion, para agregarlo
                                a la cola de tiempos.
                                */
                                if (aux.proceso.getTiempoEjecucion() > 0) {
                                    comprobandoTiempoLlegada(colaListos, colaProcesos);
                                    colaListos.insertarProceso(aux.proceso);
                                }else{
                                    comprobandoTiempoLlegada(colaListos, colaProcesos);
                                    colaTiempos.insertarProceso(aux.proceso);
                                }
                                //Eliminamos el proceso de la cola de memoria.
                                colaMem.eliminarProceso(aux.proceso);
                                aux = aux.siguiente;
                            }
                        }
                }else{
                        System.out.println("No hay procesos en la cola de memoria.");
                        break;
                }
                //tiempo++;
            }while(!colaListos.vacia());
            //Imprimos los tiempos de cada uno de los procesos.
            colaTiempos.listarColaConTiempos();
            //Realizamos el calculo promedio de los procesos.
            calcularTiempos(colaTiempos);
            //Colocamos la variable tiempo en 0, para posteriores usos.
            tiempo = 0;
        }
        
        public static void prioridadesApropiativo(){
            colaMem = new Cola();
            colaListos = new Cola();
            colaTiempos = new Cola();
            Proceso procesoEnCPU = null;
            Scanner teclado = new Scanner(System.in);
            System.out.println("¿Cuántos procesos simularemos?");
            numProcesos = teclado.nextInt();
            System.out.println("Ingresa el tamanho de la memoria en bytes:");
            memoriaTotal = teclado.nextInt();
            //se llena cola de procesos Listos
            for (int i = 0; i < numProcesos; i++) {
                 colaListos.insertarProceso(Menu.creaProceso());
            }
            //se ordena colaListos por tiempos de llegada
            colaListos.ordenarPorTiempoDeLlegada();
          
            /*primer proceso en subir es el primer proceso en cola listos*/
            tiempo=colaListos.getHead().proceso.getTiempoLlegada();
            procesoEnCPU=colaListos.getHead().proceso;
            procesoEnCPU.setTiempoEnSubirCPU(tiempo);
            colaListos.sacarProceso();
            
            
            while(procesoEnCPU!=null){
                tiempo++;          
                System.out.println("proceso en cpu " + procesoEnCPU.getId()+ "tiempo: " + tiempo);
                procesoEnCPU.setTiempoEjecucion(procesoEnCPU.getTiempoEjecucion()-1);
                procesoEnCPU.setUltimoTiempoEjecucion(tiempo);
                
                if(procesoEnCPU.getTiempoEjecucion() == 0){
                    procesoEnCPU.setUltimoTiempoEjecucion(tiempo);
                    colaTiempos.insertarProceso(procesoEnCPU);
                    //cola de memoria vacia
                        //cola listos esta vacia --> todo se ejecutaron
                        //
                    if (colaMem.vacia()) {
                        if (!colaListos.vacia()) {
                           
                            procesoEnCPU = colaListos.getHead().proceso;
                            procesoEnCPU.ultimoTiempoEspera=tiempo;
                            colaListos.sacarProceso();
                        }else{
                            procesoEnCPU=null;
                        }   
                    }else{
                            procesoEnCPU = colaMem.getHead().proceso;
                            procesoEnCPU.ultimoTiempoEspera=tiempo;
                            if(procesoEnCPU.tiempoEnSubirCPU==-1){
                                   procesoEnCPU.tiempoEnSubirCPU=tiempo;
                            }
                            colaMem.sacarProceso();
                    }
                }
                
                    while(!colaListos.vacia() && colaListos.getHead().proceso.getTiempoLlegada() == tiempo){
                     if(colaListos.getHead().proceso.getPrioridad() > procesoEnCPU.getPrioridad()){
                                System.out.println("El proceso : "+ colaListos.getHead().proceso.getId() +" apropio la CPU y el proceso "+procesoEnCPU.getId()+" baja a cola de listos para ejecutar.");
                                colaMem.insertarProcesoPorPrioridad(procesoEnCPU);
                                //colaMem.insertarProceso(procesoEnCPU);
                                //colaMem.ordenarPorPrioridad();
                                procesoEnCPU.setTiempoQueYaSeEjecuto(tiempo-procesoEnCPU.getTiempoLlegada());
                                colaListos.getHead().proceso.ultimoTiempoEspera=tiempo;
                                memoriaTotal+=procesoEnCPU.getTamanho();
                                procesoEnCPU=colaListos.getHead().proceso;
                                if(procesoEnCPU.tiempoEnSubirCPU==-1){
                                   procesoEnCPU.tiempoEnSubirCPU=tiempo;
                                }
                                memoriaTotal-=procesoEnCPU.getTamanho();
                                colaListos.sacarProceso();
                                System.out.println("Restan : "+memoriaTotal+" de memoria.");
                            }else{
                                colaMem.listarCola();
                                colaMem.insertarProcesoPorPrioridad(colaListos.getHead().proceso);
                                colaMem.listarCola();
                                System.out.println(colaListos.getHead().proceso.getPrioridad());
                                //colaMem.insertarProceso(colaListos.getHead().proceso);
                                //colaMem.ordenarPorPrioridad();
                                memoriaTotal-=colaListos.getHead().proceso.getTamanho();
                                System.out.println("Se subio el proceso : "+colaListos.getHead().proceso.getId()+" y restan "+memoriaTotal+" de memoria.");
                                colaListos.sacarProceso();
                            }
                    
                    }
                   
           
                
                
                
                
                
            }


            
            System.out.println("colaTiempos");
            colaTiempos.listarColaConTiempos();
            calcularTiempos(colaTiempos);
            
            
        }
        
        public static void comprobandoTiempoLlegada(Cola colaListos, Cola colaProcesos){
            Nodo p = colaProcesos.head;
                while(p != null){
                    if (p.proceso.getTiempoLlegada() <= tiempo) {
                        colaListos.insertarProceso(p.proceso);
                        colaProcesos.eliminarProceso(p.proceso);
                    }
                    p = p.siguiente;
                }
        }
        
	public static void ejecutandoProceso(Proceso p, int Q){
		int seg = Q;
		while(p.getTiempoEjecucion() != 0 && Q != 0) {
			if (p.tiempoEnSubirCPU == -1) {
				p.tiempoEnSubirCPU = tiempo;
			}
			System.out.println("   "+tiempo+"	Proceso " + p.getId() + " en ejecucion: " + p.getTiempoEjecucion());
			p.setTiempoEjecucion(p.getTiempoEjecucion()-1);
			p.ultimoTiempoEjecucion = tiempo+1;
			Q--;
			p.ultimoTiempoEspera = p.ultimoTiempoEjecucion - (seg - Q);
                        p.tiempoQueYaSeEjecuto = p.constanteTiempoEjecucion - (seg - Q);
			tiempo++;
		}
	}

	public static void calcularTiempos(Cola colaTiempos){
		if (!colaTiempos.vacia()) {
			int procesos = 0;
			float tiempoEspera = 0, tiempoEjecucion = 0, tiempoEnSubirCPU = 0;
			Nodo p = colaTiempos.head;
			while(p != null){
				tiempoEspera += p.proceso.ultimoTiempoEspera-p.proceso.tiempoQueYaSeEjecuto-p.proceso.getTiempoLlegada();
				tiempoEnSubirCPU += p.proceso.tiempoEnSubirCPU - p.proceso.getTiempoLlegada();
				tiempoEjecucion += p.proceso.ultimoTiempoEjecucion - p.proceso.getTiempoLlegada() ;
				p = p.siguiente;
				procesos++;
			}
                        System.out.println("Procesos en cola: "+procesos);
			tiempoEspera = tiempoEspera/procesos;
			tiempoEnSubirCPU = tiempoEnSubirCPU/procesos;
			tiempoEjecucion = tiempoEjecucion/procesos;
			System.out.println("El tiempo promedio de espera es: "+tiempoEspera+" ms");
			System.out.println("El tiempo promedio de ejecucion es: "+tiempoEjecucion+" ms");
			System.out.println("El tiempo promedio en subir al CPU es: "+tiempoEnSubirCPU+" ms");
		}
	}
}

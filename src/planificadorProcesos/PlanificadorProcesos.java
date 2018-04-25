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
    private static int numProcesos;
    private static int memoriaTotal;

	//Variable que llevara el conteo del tiempo transcurrido de ejecucion.
	public static int tiempo = 0;

	//Metodo que realiza todo el planificador Round Robin, recibe una cola de procesos listos.
	public static void roundRobin(){
            /*
            memoriaTotal: almacena el tamaño de la memoria dada por el usuario.
            memoria: almacenara el mismo valor que memoriaTotal, pero modificaremos su valor
            durante las iteraciones.
            Q: almacenara el quantum para cada ejecucion del procesador.
            */
            int memoria, quantum;
            colaMem = new Cola();
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
                 colaListos.insertarProceso(Menu.creaProcesoRoundRobin());
            }
            System.out.println("Cola de procesos en memoria, con una memoria de: "+ memoriaTotal);

            //While que comprueba que la cola de procesos listos no este vacia.
            while(!colaListos.vacia()){
                    memoria = memoriaTotal;
                    p = colaListos.head;
                    System.out.println("Cola de procesos listos:");
                    colaListos.listarCola();
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
                                    //Simluamos la ejecucion en CPU del proceso, con el quantum.
                                    ejecutandoProceso(aux.proceso, quantum);
                                    /*
                                    Comprobamos si el proceso ya termino su ejecucion, para agregarlo
                                    a la cola de tiempos.
                                    */
                                    if (aux.proceso.getTiempoEjecucion() > 0) {
                                            colaListos.insertarProceso(aux.proceso);
                                    }else{
                                            colaTiempos.insertarProceso(aux.proceso);
                                    }
                                    //Eliminamos el proceso de la cola de memoria.
                                    colaMem.eliminarProceso(aux.proceso);
                                    aux = aux.siguiente;
                            }
                    }else{
                            System.out.println("No hay procesos en la cola de memoria.");
                            break;
                    }
            }
            //Imprimos los tiempos de cada uno de los procesos.
            colaTiempos.listarColaConTiempos();
            //Realizamos el calculo promedio de los procesos.
            calcularTiempos(colaTiempos);
            //Colocamos la variable tiempo en 0, para posteriores usos.
            tiempo = 0;
	}
        
        public static void roundRobinLlegada(){
            /*
            memoriaTotal: almacena el tamaño de la memoria dada por el usuario.
            memoria: almacenara el mismo valor que memoriaTotal, pero modificaremos su valor
            durante las iteraciones.
            Q: almacenara el quantum para cada ejecucion del procesador.
            */
            int memoria, quantum;
            colaMem = new Cola();
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
                 colaListos.insertarProceso(Menu.creaProceso());
            }
            System.out.println("Cola de procesos en memoria, con una memoria de: "+ memoriaTotal);
            
            colaListos.ordenarPorTiempoDeLlegada();
            colaListos.listarCola();
            
            //While que comprueba que la cola de procesos listos no este vacia.
            while(!colaListos.vacia()){
                memoria = memoriaTotal;
                p = colaListos.head;
                System.out.println("Cola de procesos listos:");
                colaListos.listarCola();
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
                                        colaListos.insertarProceso(aux.proceso);
                                }else{
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
            }
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
            tiempo=colaListos.getHead().proceso.getTiempoLlegada();
            while(!colaListos.vacia()){
                if(colaListos.getHead().proceso.getTamanho() <= memoriaTotal ){
                    while(!colaListos.vacia() && colaListos.getHead().proceso.getTiempoLlegada()==tiempo){
                        if(procesoEnCPU == null){
                        colaListos.getHead().proceso.ultimoTiempoEspera=tiempo;
                         procesoEnCPU=colaListos.getHead().proceso;
                         colaListos.sacarProceso();
                         memoriaTotal-=procesoEnCPU.getTamanho();
                         System.out.println("Se subio el proceso : "+procesoEnCPU.getId()+" a CPU y restan "+memoriaTotal+" de memoria.");
                         procesoEnCPU.setTiempoEnSubirCPU(tiempo);
                                            
                     }else{
                           //Si el siguiente proceso en cola de listos, tiene una prioridad mayor al que está en cpu,
                           //se apropia CP
                           if(colaListos.getHead().proceso.getPrioridad() > procesoEnCPU.getPrioridad() ){
                          
                                procesoEnCPU.setUltimoTiempoEjecucion(tiempo);
                                 procesoEnCPU.setTiempoQueYaSeEjecuto(procesoEnCPU.tiempoQueYaSeEjecuto+(procesoEnCPU.ultimoTiempoEjecucion-procesoEnCPU.ultimoTiempoEspera));
                                 procesoEnCPU.setTiempoEjecucion(procesoEnCPU.getTiempoEjecucion()-procesoEnCPU.tiempoQueYaSeEjecuto);
                                 System.out.println("El proceso : "+ colaListos.getHead().proceso.getId() +" apropio la CPU y el proceso "+procesoEnCPU.getId()+" baja a cola de listos para ejecutar.");
                                 colaListos.getHead().proceso.tiempoEnSubirCPU = tiempo;
                                 System.out.println("Restan : "+memoriaTotal+" de memoria.");
                                colaMem.insertarProcesoPorPrioridad(procesoEnCPU);
                                colaListos.getHead().proceso.ultimoTiempoEspera=tiempo;
                                memoriaTotal+=procesoEnCPU.getTamanho();
                                procesoEnCPU=colaListos.getHead().proceso;
                                memoriaTotal-=procesoEnCPU.getTamanho();
                                colaListos.sacarProceso();
                            }else{
                                colaMem.insertarProcesoPorPrioridad(colaListos.getHead().proceso);
                                memoriaTotal-=colaListos.getHead().proceso.getTamanho();
                                System.out.println("Se subio el proceso : "+colaListos.getHead().proceso.getId()+" y restan "+memoriaTotal+" de memoria.");
                                colaListos.sacarProceso();
                            }
                        }
                    }
                    
                    
                }else{
                   System.out.println("No hay memoria suficiente");
                   break;
                }
                tiempo+=1;
            }
            tiempo-=1;

            while(procesoEnCPU != null){
                for (int i = 0; i < procesoEnCPU.getTiempoEjecucion(); i++) {
                    tiempo++;
                    procesoEnCPU.setTiempoEjecucion(procesoEnCPU.getTiempoEjecucion()-1);
                }
                
                System.out.println("tiempo: " + tiempo);
                procesoEnCPU.setUltimoTiempoEjecucion(tiempo);
                if(procesoEnCPU.getTiempoEjecucion()<=0){
                    if(colaMem.vacia() != true){
                    colaTiempos.insertarProceso(procesoEnCPU);
                    procesoEnCPU=colaMem.getHead().proceso;
                    if(procesoEnCPU.tiempoEnSubirCPU==-1)
                        procesoEnCPU.tiempoEnSubirCPU=tiempo;
                    System.out.println(" ahora debe terminar de ejecutarse" + procesoEnCPU.getId());
                    procesoEnCPU.setUltimoTiempoEspera(tiempo);
                    colaMem.eliminarProceso(procesoEnCPU);   
                    }  else{
                        colaTiempos.insertarProceso(procesoEnCPU);
                        procesoEnCPU=null;
                    }
                }
         
            }
            
            colaTiempos.listarColaConTiempos();
            calcularTiempos(colaTiempos);
            
            
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

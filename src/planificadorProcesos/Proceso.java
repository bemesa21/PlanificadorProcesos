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
//Clase que define un proceso.
public class Proceso{

    private int idProceso;
    private String nombreProceso;
    private int tamanho;
    private int tiempoDeEjecucion;
    private int prioridad;
    private int tiempoLlegada;
    public int ultimoTiempoEjecucion;
    public int ultimoTiempoEspera;
    public int tiempoEnSubirCPU;
    public int tiempoQueYaSeEjecuto;
    public int constanteTiempoEjecucion;

    public void setUltimoTiempoEspera(int ultimoTiempoEspera) {
        this.ultimoTiempoEspera = ultimoTiempoEspera;
    }

    public void setUltimoTiempoEjecucion(int ultimoTiempoEjecucion) {
        this.ultimoTiempoEjecucion = ultimoTiempoEjecucion;
    }

    public void setTiempoEnSubirCPU(int tiempoEnSubirCPU) {
        this.tiempoEnSubirCPU = tiempoEnSubirCPU;
    }

    public void setTiempoQueYaSeEjecuto(int tiempoQueYaSeEjecuto) {
        this.tiempoQueYaSeEjecuto = tiempoQueYaSeEjecuto;
    }

    public Proceso(){}

    public int getTamanho(){return tamanho;}
    public int getTiempoLlegada(){return tiempoLlegada;}
    public int getPrioridad(){return prioridad;}
    public String getName(){return nombreProceso;}
    public int getId(){return idProceso;}
    public int getTiempoEjecucion(){return tiempoDeEjecucion;}
    public void setTiempoEjecucion(int tiempoDeEjecucion){
            this.tiempoDeEjecucion =  tiempoDeEjecucion < 0 ? 0 : tiempoDeEjecucion ;
    }

    public Proceso(int idProceso,String nombreProceso,int tamanho,int tiempoDeEjecucion,int prioridad,int tiempoLlegada){
        this(idProceso, tamanho, tiempoDeEjecucion);
        this.nombreProceso = nombreProceso;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        tiempoEnSubirCPU = -1;
    }

    public Proceso(int idProceso, int tamanho, int tiempoDeEjecucion) {
        this.idProceso = idProceso;
        this.tamanho = tamanho;
        this.tiempoDeEjecucion = tiempoDeEjecucion;
        constanteTiempoEjecucion = tiempoDeEjecucion;
        tiempoEnSubirCPU = -1;
    }
    
    public void datosProceso(){
            System.out.printf("\nId: " + idProceso+"\n");
            System.out.println("Nombre: " + nombreProceso);
            System.out.println("Tiempo ejecucion: "+tiempoDeEjecucion);
            System.out.println("Tamanho: " + tamanho);
            System.out.println("Tiempo de llegada: "+tiempoLlegada);
            System.out.println("");
    }

    public void tiemposProceso(){
            System.out.printf("1Id: " + idProceso+"\n");
            System.out.println("ultimoTiempoEjecucion: " + ultimoTiempoEjecucion);
            System.out.println("ultimoTiempoEspera: "+ ultimoTiempoEspera);
            System.out.println("tiempoEnSubirCPU: " + tiempoEnSubirCPU);
            System.out.println("Tiempo que ya se ejecutó antes: " + tiempoQueYaSeEjecuto);
            System.out.println("Tiempo en que llegó: " + tiempoLlegada);
            System.out.println("\nTiempo de ejecucion restante: " + tiempoDeEjecucion);
            System.out.println("");
    }

}
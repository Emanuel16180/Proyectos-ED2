/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import excepciones.ClaveNoExisteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Emanuel
 * @param <K>
 * @param <V>
 */
public class ArbolMViasDeBusqueda <K extends Comparable<K>,V> implements IArbolBusqueda<K,V> {
    
    protected NodoMVias<K,V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;
    
    
    public ArbolMViasDeBusqueda(){
        this.orden=3;
    }
    
    public ArbolMViasDeBusqueda(int orden){                        
        if(orden<3){
            throw new RuntimeException("orden invalido");
        }
        this.orden=orden;
    }
    
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if(esArbolVacio()){
             this.raiz=new NodoMVias<>(orden,claveAInsertar,valorAInsertar);
             return;
         }
         NodoMVias<K,V>nodoActual=this.raiz;
                while(!NodoMVias.esNodoVacio(nodoActual)){
                    int posicionClaveEnNodo=existeClaveEnNodo(nodoActual,claveAInsertar);
                    if(posicionClaveEnNodo!=POSICION_INVALIDA){
                        nodoActual.setValor(posicionClaveEnNodo, valorAInsertar);
                        nodoActual=NodoMVias.nodoVacio();
                    }
                    if(nodoActual.esHoja()){
                        if(!nodoActual.estanClavesLlenas()){
                            insertarDatosOrdenadosEnNodo(nodoActual,claveAInsertar,valorAInsertar); 
                        }else{
                             int posicionPorDondeBajar=porDondeBajar(nodoActual,claveAInsertar);
                             NodoMVias<K,V>nuevo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                             nodoActual.setHijo(posicionPorDondeBajar,nuevo);
                        }
                        nodoActual=NodoMVias.nodoVacio();
                    }else{
                        int posicionBajar=porDondeBajar(nodoActual,claveAInsertar);                            
                            if(NodoMVias.esNodoVacio(nodoActual.getHijo(posicionBajar))){
                                NodoMVias<K,V>nuevoHijo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                                nodoActual.setHijo(posicionBajar,nuevoHijo);
                                nodoActual=NodoMVias.nodoVacio();
                            }else{
                                nodoActual=nodoActual.getHijo(posicionBajar);
                            }
                    }
                }
    }
    
    private int porDondeBajar(NodoMVias<K,V>nodoActual,K claveABuscar){
        int i=0;
        boolean llegoAlFinal=false;
            while(i<nodoActual.cantidadDeClavesNoVacias()){
                K claveActual=nodoActual.getClave(i);
                    if(claveActual.compareTo(claveABuscar)<0){
                        i++;
                    }else{
                        break;
                    }       
            }
            if(nodoActual.getClave(nodoActual.cantidadDeClavesNoVacias()-1).compareTo(claveABuscar)<0){
                return nodoActual.cantidadDeClavesNoVacias();
            }
            return i;
    }
    
    private void insertarDatosOrdenadosEnNodo(NodoMVias<K,V>nodoActual,K claveAInsertar,V valorAInsertar){
        int res=0;
        for(int i=nodoActual.cantidadDeClavesNoVacias()-1;i>=0;i--){
                K claveActual=nodoActual.getClave(i);
                    if(claveActual.compareTo(claveAInsertar)>0){
                        nodoActual.setClave(i+1, claveActual);
                    }else{
                     res=i;
                     break;
                    }
          
        }
        nodoActual.setClave(res+1, claveAInsertar);
        nodoActual.setValor(res+1, valorAInsertar);
       
    }
    
    private int existeClaveEnNodo(NodoMVias<K,V>nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            K claveActual=nodoActual.getClave(i);
                if(claveActual.compareTo(claveABuscar)==0){
                    return i;
                }
        }
        return POSICION_INVALIDA;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if(claveAEliminar==null){
            throw new IllegalArgumentException("La clave no puede ser nula");
        }
        V valorRetorno=this.buscar(claveAEliminar);
        if(valorRetorno!=null){
            this.raiz = eliminar(this.raiz,claveAEliminar);
            return valorRetorno;
        }
        throw new IllegalArgumentException("La clave no Existe en el arbol");
    }
    
    private NodoMVias<K,V> eliminar(NodoMVias<K,V>nodoActual,K claveAEliminar){
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                K claveActual=nodoActual.getClave(i);
                if(claveActual.compareTo(claveAEliminar)==0){
                    if(nodoActual.esHoja()){
                        this.eliminarDatoDelNodo(nodoActual,i);
                        if(nodoActual.cantidadDeClavesNoVacias()==0){
                            return NodoMVias.nodoVacio();
                        }
                        return nodoActual;
                    }//else{//SI SE LLEGA ACA ENTOCES LA CLAVE NO ESTA EN UNA HOJA
                          K claveReemplazo;
                            if(this.hayDatosMasAdelante(nodoActual,i)){
                             claveReemplazo=this.buscarSucesorEnInOrden(nodoActual,claveAEliminar);
                            }else{
                             claveReemplazo=this.buscarPredecesorEnInOrden(nodoActual,claveAEliminar);    
                            }
                            V valorDeReemplazo=this.buscar(claveReemplazo);
                            nodoActual=eliminar(nodoActual,claveReemplazo);
                            nodoActual.setClave(i, claveReemplazo);
                            nodoActual.setValor(i, valorDeReemplazo);
                            return nodoActual;
                    //}           
                }
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoMVias<K,V>posibleHijo=this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                    nodoActual.setHijo(i, posibleHijo);
                    return nodoActual;
                    
                }
        }//SI LLEGA AQUI EL FINAL DEL FOR HAY QUE BUSCAR POR EL LADO DERECHO DEL NODO LA POSICION ORDEN;
            NodoMVias<K,V>supuesto=this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),claveAEliminar);
            nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(), supuesto);
            return nodoActual;
        
    }


    
    private K buscarPredecesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
     K claveDeRetorno=(K)NodoMVias.datoVacio();
     int posicion=this.porDondeBajar(nodoActual, claveABuscar);
     NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(0);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
        return claveDeRetorno;
    }
    
    private K buscarSucesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
        int posicion=this.porDondeBajar(nodoActual, claveABuscar)+1;
        K claveDeRetorno=(K)NodoMVias.datoVacio();
        NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(nodoAuxiliar.cantidadDeClavesNoVacias()-1);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
      return claveDeRetorno;      
    }
    
    private boolean hayDatosMasAdelante(NodoMVias<K,V>nodoActual,int posicion){   
      return nodoActual.cantidadDeClavesNoVacias()-1>posicion;
    }
    
    private void eliminarDatoDelNodo(NodoMVias<K,V>nodoActual,int posicion){
        int i=posicion;
        for(;i<=nodoActual.cantidadDeClavesNoVacias()-1;i++){
            nodoActual.setClave(i,nodoActual.getClave(i+1));
            nodoActual.setValor(i,nodoActual.getValor(i+1));
        }
    }

    @Override
    public V buscar(K claveABuscar) {
        if(esArbolVacio()){
            return null;
        }
        NodoMVias<K,V>nodoActual=this.raiz;  
            while(!NodoMVias.esNodoVacio(nodoActual)){
                boolean huboCambioDeNodoActual=false;
                for(int i=0;i<nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual;i++){
                    K claveActual=nodoActual.getClave(i);
                    if(claveABuscar.compareTo(claveActual)== 0){
                        return nodoActual.getValor(i);
                    }
                    if(claveABuscar.compareTo(claveActual) < 0){
                        huboCambioDeNodoActual = true;
                        nodoActual=nodoActual.getHijo(i);
                    }
                        
                } // fin del for 
                if(!huboCambioDeNodoActual){
                    nodoActual=nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
                }
               
            }
        return (V)NodoMVias.datoVacio();
    }

    @Override
    public boolean contiene(K clave) {
        return buscar(clave)!= NodoMVias.datoVacio();
    }

    @Override
    public int size() {
        if(esArbolVacio()){
            return 0;
        }
        int cantidad=0;
        NodoMVias<K,V>nodoActual=NodoMVias.nodoVacio();
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                nodoActual=colaDeNodos.poll();
                cantidad++;
                    for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                        if(!nodoActual.esHijoVacio(i)){
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        }
                    }
                    if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                    }
            }
            return cantidad;
    }

    @Override
     public int altura() {
        if(esArbolVacio()){
            return 0;
        }
        int alturaDelArbol=0;
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        NodoMVias<K,V>nodoActual=NodoMVias.nodoVacio();
        while(!colaDeNodos.isEmpty()){
            int cantidadDeNodosEnLaCola=colaDeNodos.size();
            int i=0;
            while(i<cantidadDeNodosEnLaCola){
              nodoActual=colaDeNodos.poll();
                for(int x=0;x<nodoActual.cantidadDeClavesNoVacias();x++){
                    if(!nodoActual.esHijoVacio(x)){
                        colaDeNodos.offer(nodoActual.getHijo(x));
                    }
                }
                if(!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
                i++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }

    @Override
    public void vaciar() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
       return altura()-1;
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K>listaDeClaves=new LinkedList<>();
        if(esArbolVacio()){
            return listaDeClaves;
        }
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                NodoMVias<K,V>nodoActual=colaDeNodos.poll();
                for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                    listaDeClaves.add(nodoActual.getClave(i));
                    if(!NodoMVias.esNodoVacio(nodoActual.getHijo(i))){
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if(!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
                 
            }
        return listaDeClaves;
    } 

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K>recorrido=new LinkedList<>();
        recorridoEnPreOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        recorrido.add(nodoActual.getClave(0));
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden() {
       List<K>recorrido=new LinkedList<>();
       recorridoEnInOrden(this.raiz,recorrido);
       return recorrido;
    }
    
    private void recorridoEnInOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }
    
    @Override
    public List<V> recorridoEnInOrdenV() {
       List<K>recorrido=new LinkedList<>();
       List<V>recorridoV=new LinkedList<>();
       recorridoEnInOrdenV(this.raiz,recorrido,recorridoV);
       return recorridoV;
    }
    
    private void recorridoEnInOrdenV(NodoMVias<K,V>nodoActual,List<K>recorrido,List<V>recorridoV){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnInOrdenV(nodoActual.getHijo(i),recorrido,recorridoV);
            recorrido.add(nodoActual.getClave(i));
            recorridoV.add(nodoActual.getValor(i));
        }
        recorridoEnInOrdenV(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido,recorridoV);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K>recorrido=new LinkedList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return ;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0),recorrido);
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }   
    }
    
    
    @Override
    public String toString() {
        String muestra = "";
        if(esArbolVacio()){
            return "Arbol Vacio";
        }
        int alturaDelArbol=0;
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        NodoMVias<K,V>nodoActual=NodoMVias.nodoVacio();
        while(!colaDeNodos.isEmpty()){
            int cantidadDeNodosEnLaCola=colaDeNodos.size();
            int i=0;
            while(i<cantidadDeNodosEnLaCola){
              nodoActual=colaDeNodos.poll();
              muestra = muestra + nodoActual.toString() + " ";
                for(int x=0;x<nodoActual.cantidadDeClavesNoVacias();x++){
                    if(!nodoActual.esHijoVacio(x)){
                        colaDeNodos.offer(nodoActual.getHijo(x));
                    }
                }
                if(!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
                
                i++;
            }
            muestra=muestra+"\n";
            alturaDelArbol++;
        }
        return muestra;
    }
    
}

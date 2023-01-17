/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import excepciones.ClaveNoExisteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Emanuel
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda <K extends Comparable<K>, V> implements 
        IArbolBusqueda<K, V> {
    protected NodoBinario<K, V> raiz;

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        //Valida que las entradas no sean nulas.
        if(claveAInsertar == null){
            throw new IllegalArgumentException("Clave a insertar no puede ser nula");
        }
        if(valorAsociado == null){
            throw new IllegalArgumentException("Valor asociado a la clave no puede ser nulo");
        }
        
        //Caso 1: El arbol esta vacio y se tiene que insertar el primer nodo.
        if(this.esArbolVacio()){
            this.raiz = new NodoBinario(claveAInsertar,valorAsociado);
            return;
        }
        
        //Caso 2: La clave ya esta en el arbol, actualizamos el valor.
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if(claveAInsertar.compareTo(claveActual)<0){
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                if(claveAInsertar.compareTo(claveActual)>0){
                    nodoAnterior = nodoActual;
                    nodoActual = nodoActual.getHijoDerecho();
                } else {
                    nodoActual.setValor(valorAsociado);
                    return;
                }
            }
        }
        
        //Caso 3: La clave no esta en el arbol y toca enlazar el nuevo nodo.
        //Se enlaza al nodo anterior como su padre.
        NodoBinario<K, V> nuevoNodoBinario = new NodoBinario(claveAInsertar, valorAsociado);
        K claveDelNodoAnterior = nodoAnterior.getClave();
        if(claveAInsertar.compareTo(claveDelNodoAnterior)<0){
            nodoAnterior.setHijoIzquierdo(nuevoNodoBinario);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodoBinario);
        } 
    }

    @Override
    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
        if(claveAEliminar == null){
            throw new IllegalArgumentException("Clave a eliminar no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        this.raiz = eliminar(this.raiz,claveAEliminar);
        return valorARetornar;
    }
    
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar){
        K claveActual = nodoActual.getClave();
        if(claveAEliminar.compareTo(claveActual)>0){
            NodoBinario<K,V> posibleNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(posibleNuevoHijoDerecho);
            return nodoActual;
        }
        
        if(claveAEliminar.compareTo(claveActual)<0){
            NodoBinario<K,V> posibleNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHijoIzquierdo);
            return nodoActual;
        }
        
        //Si llega a este punto quiere decir que ya encontre el nodo con la clave a eliminar
        //Caso 1: esHoja
        if(nodoActual.esHoja()){
            return (NodoBinario<K, V>)NodoBinario.nodoVacio();
        }
        
        //Caso 2
        //Caso 2.1 solo tiene hijo izquierdo
        if(!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio()){
            return nodoActual.getHijoIzquierdo();
        }
        
        //Caso 2.2 solo tiene derecho
        if(nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()){
            return nodoActual.getHijoDerecho();
        }
        
        //Caso 3
        
        NodoBinario<K,V> nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> posibleNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave());
        
        nodoActual.setHijoDerecho(posibleNuevoHijoDerecho);
        
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        
        return nodoActual;
    }
    
    protected NodoBinario<K,V> buscarNodoSucesor(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    }

    @Override
    public V buscar(K claveABuscar) {
        //Valida que la entrada no sea nula.
        if (claveABuscar == null) {
            throw new IllegalArgumentException("Clave a buscar no puede ser nula");
        }
        
        //Busca nodo a nodo en el arbol y si encuentra la clave, devuelve el
        //valor asociado.
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                if (claveABuscar.compareTo(claveActual) > 0) {
                    nodoActual = nodoActual.getHijoDerecho();
                } else {
                    return nodoActual.getValor();
                }
            }
        }
        
        //Si no encuentra la clave en el arbol devuelve nulo.
        return null;
    }

    @Override
     public boolean contiene(K claveABuscar) {
        //Valida que la entrada no sea nula.
        if (claveABuscar == null) {
            throw new IllegalArgumentException("Clave a buscar no puede ser nula");
        }
        
        //Busca nodo a nodo en el arbol y si encuentra la clave, devuelve 
        //verdadero.
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                if (claveABuscar.compareTo(claveActual) > 0) {
                    nodoActual = nodoActual.getHijoDerecho();
                } else {
                    return true;
                }
            }
        }
        
        //Si no encuentra la clave en el arbol devuelve falso.
        return false;
    }

    @Override
    public int size() {
        int cantidadDeNodos = 0;
        if(!this.esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while(!pilaDeNodos.isEmpty()){
                NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
                cantidadDeNodos++;
                if(!nodoActual.esHijoDerechoVacio()){
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if(!nodoActual.esHijoIzquierdoVacio()){
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return cantidadDeNodos;
    }

    @Override
    public int altura() {
        int altura = 0;
        if(!this.esArbolVacio()){
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                int tamañoDeLaCola = colaDeNodos.size();
                for (int i = 0; i < tamañoDeLaCola; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if(!nodoActual.esHijoIzquierdoVacio()){
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if(!nodoActual.esHijoDerechoVacio()){
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }            
                }
                altura++;  
            }
        }
        return altura;
    }

    public int alturaR(){
        return alturaR(this.raiz);
    }
    
    protected int alturaR(NodoBinario<K,V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        
        int alturaPorIzquierda = alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = alturaR(nodoActual.getHijoDerecho());
        
        return (alturaPorIzquierda>alturaPorDerecha) ? (alturaPorIzquierda+1) : (alturaPorDerecha+1);
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
         return (this.altura()-1);
    }

    @Override
     public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if(!this.esArbolVacio()){
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                recorrido.add(nodoActual.getClave());
                if(!nodoActual.esHijoIzquierdoVacio()){
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if(!nodoActual.esHijoDerechoVacio()){
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while(!pilaDeNodos.isEmpty()){
                NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                if(!nodoActual.esHijoDerechoVacio()){
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if(!nodoActual.esHijoIzquierdoVacio()){
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            NodoBinario<K,V> nodoActual = this.raiz;
            
            while(!pilaDeNodos.isEmpty() || !NodoBinario.esNodoVacio(nodoActual)){
                //Intrudusco los nodos por el lado izquierdo hasta que se caiga.
                while (!NodoBinario.esNodoVacio(nodoActual)) {
                    pilaDeNodos.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }
                //Me posiciono en el nodo anterior a caerme.
                NodoBinario<K,V> nodoAnterior = pilaDeNodos.pop();
                recorrido.add(nodoAnterior.getClave());
                nodoActual = nodoAnterior.getHijoDerecho();
            } 
            
        }
        return recorrido;
    }
    
    @Override
    public List<V> recorridoEnInOrdenV() {
        List<K> recorrido = new ArrayList<>();
        List<V> recorridoV = new ArrayList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            NodoBinario<K,V> nodoActual = this.raiz;
            
            while(!pilaDeNodos.isEmpty() || !NodoBinario.esNodoVacio(nodoActual)){
                //Intrudusco los nodos por el lado izquierdo hasta que se caiga.
                while (!NodoBinario.esNodoVacio(nodoActual)) {
                    pilaDeNodos.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }
                //Me posiciono en el nodo anterior a caerme.
                NodoBinario<K,V> nodoAnterior = pilaDeNodos.pop();
                recorrido.add(nodoAnterior.getClave());
                recorridoV.add(nodoAnterior.getValor());
                nodoActual = nodoAnterior.getHijoDerecho();
            } 
            
        }
        return recorridoV;
    }
    

    @Override
     public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if(!this.esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            NodoBinario<K,V> nodoActual = this.raiz;
            while(!pilaDeNodos.isEmpty() || !NodoBinario.esNodoVacio(nodoActual)){
                while (!NodoBinario.esNodoVacio(nodoActual)) {
                    pilaDeNodos.push(nodoActual);
                    if(!nodoActual.esHijoIzquierdoVacio()){
                         nodoActual = nodoActual.getHijoIzquierdo();
                    } else {
                         nodoActual = nodoActual.getHijoDerecho();
                    }
                }
                
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                
                if(!pilaDeNodos.isEmpty()){
                    NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                    if(!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho()!= nodoActual){
                        nodoActual = nodoDelTope.getHijoDerecho();
                    } else {
                        nodoActual = null;
                    }
                } else {
                     nodoActual = null;
                }
            }
        }
        return recorrido;
    }
    
    
    @Override
    public String toString() {
        return toString(this.raiz,"","",false);
    }
    
    private String toString(NodoBinario<K, V> nodoActual, String representacion,
            String nivel, boolean esIzquierdo) {
        representacion += nivel;
        if (!nivel.equals("")) {
            representacion += "\b\b" + (esIzquierdo ? "\u251C": "\u2514") + "\u2500";
        }
        if(NodoBinario.esNodoVacio(nodoActual)){
            return representacion += "\n";
        }
        
        representacion += nodoActual.getClave() + "\n";
        
        // Hijo derecho
        representacion = toString(nodoActual.getHijoDerecho(), representacion, nivel + "\u2502 ", true);
        // Hijo izquierdo
        representacion = toString(nodoActual.getHijoIzquierdo(), representacion, nivel + "  ", false);
        
        return representacion;
    }
    
}

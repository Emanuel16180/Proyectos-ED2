/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

/**
 *
 * @author Emanuel
 * @param <K>
 * @param <V>
 */
public class AVL <K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V>{
    private static final byte DIFERENCIA_MAXINA = 1; 
    public int cantidadDeRotaciones = 0;
    
    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        //Valida que las entradas no sean nulas.
        if(claveAInsertar == null){
            throw new IllegalArgumentException("Clave a insertar no puede ser nula");
        }
        if(valorAsociado == null){
            throw new IllegalArgumentException("Valor asociado a la clave no puede ser nulo");
        }
        super.raiz = this.insertar(this.raiz, claveAInsertar, valorAsociado);
        
    }
    //RECURSIVIDAD V:<
    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        
        if(NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAsociado);
            return nuevoNodo;
        }
        
        K claveActual = nodoActual.getClave();
        
        if(claveAInsertar.compareTo(claveActual)>0){
            NodoBinario<K, V> supuestoNuevoHijoDerecho = insertar(nodoActual.getHijoDerecho(), 
                    claveAInsertar, valorAsociado);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return this.balancear(nodoActual);
            //return nodoActual;
        }
        
        if(claveAInsertar.compareTo(claveActual)<0){
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(), 
                    claveAInsertar, valorAsociado);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return this.balancear(nodoActual);
            //return nodoActual;
        }
        
        //si llego acá quiere decir que en el nodo actual esta la clave a insertar
        nodoActual.setValor(valorAsociado);
        return nodoActual;
        
    }
    
    
    @Override
    public V eliminar(K claveAEliminar){
      V valorRetorno=super.buscar(claveAEliminar);
        if(valorRetorno==null){
          return null;
        }
        this.raiz=eliminar(raiz,claveAEliminar);
        return valorRetorno;
    }
    private NodoBinario<K,V>eliminar(NodoBinario<K,V>nodoActual,K claveAEliminar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return (NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
            K claveActual=nodoActual.getClave();
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoBinario<K,V>izquierdo=eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
                    nodoActual.setHijoIzquierdo(izquierdo);
                    return balancear(nodoActual);
                }
                if(claveAEliminar.compareTo(claveActual)>0){
                   NodoBinario<K,V>derecho=eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
                   nodoActual.setHijoDerecho(derecho);
                   return balancear(nodoActual);
                }
             /// SI SE LLEGA A ESTE PUNTO SE ENCONTRO LA CLAVE A ELIMINAR
             ///YA QUE LA CLAVE A ELIMINAR NO ES MENOR NI MAYOR ,SINO IGUAL
            // # caso 1 el nodo a eliminar es una hoja
            if(nodoActual.esHoja()){
                return (NodoBinario<K,V>)NodoBinario.nodoVacio();
            }
            //# CASO 2 LA CLAVE A ELIMINAR ES UN NODO INCOMPLETO
            if(nodoActual.esHijoDerechoVacio() && !nodoActual.esHijoIzquierdoVacio()){
                return balancear(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esHijoDerechoVacio() && nodoActual.esHijoIzquierdoVacio()){
                return balancear(nodoActual.getHijoDerecho());
            }
            // # CASO 3 LA CLAVE A ELIMINAR ES UN NODO COMPLETO 
            // HAY QUE BUSCAR SU NODO SUCESOR
            NodoBinario<K,V>nodoReemplazo=(super.buscarNodoSucesor(nodoActual.getHijoDerecho()));
            NodoBinario<K,V>posibleNuevoHijoDerecho=(eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave()));
           
            nodoActual.setHijoDerecho((posibleNuevoHijoDerecho));
            nodoActual.setClave(nodoReemplazo.getClave());
            nodoActual.setValor(nodoReemplazo.getValor());
            //nodoSucesor.setHijoDerecho((nodoActual.getHijoDerecho()));
            //nodoSucesor.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            //nodoActual.setHijoDerecho((NodoBinario<K,V>)NodoBinario.nodoVacio());
            //nodoActual.setHijoIzquierdo((NodoBinario<K,V>)NodoBinario.nodoVacio());
        
        return nodoActual;
    }
    
    
    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzquierda = this.alturaR(nodoActual.getHijoIzquierdo());
        int alturaRamaDerecha = this.alturaR(nodoActual.getHijoDerecho());
        int diferencia = alturaRamaIzquierda-alturaRamaDerecha;
        
        if(diferencia > DIFERENCIA_MAXINA){
            // Toca balancear
            this.cantidadDeRotaciones++;
            NodoBinario<K,V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzquierda = alturaR(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDerecha = alturaR(hijoIzquierdo.getHijoDerecho());
            if(alturaRamaDerecha>alturaRamaIzquierda){
                return this.rotacionDobleADerecha(nodoActual);
            } else {
                return this.rotacionSimpleADerecha(nodoActual);
            }
        } else { 
            if(diferencia < -DIFERENCIA_MAXINA){
            this.cantidadDeRotaciones++;
            NodoBinario<K,V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzquierda = alturaR(hijoDerecho.getHijoIzquierdo());
            alturaRamaDerecha = alturaR(hijoDerecho.getHijoDerecho());
            if(alturaRamaIzquierda>alturaRamaDerecha){
                return this.rotacionDobleAIzquierda(nodoActual);
            } else {
                return this.rotacionSimpleAIzquierda(nodoActual);
            }
   
            }
        }
        
        return nodoActual;
    }
        

    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual){
        NodoBinario<K,V> nodoQueRotaAIzquierda = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoQueRotaAIzquierda);
        return this.rotacionSimpleADerecha(nodoActual);
    }
    
    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual){
        NodoBinario<K,V> nodoQueRotaADerecha = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoQueRotaADerecha);
        return this.rotacionSimpleAIzquierda(nodoActual);
    }
}

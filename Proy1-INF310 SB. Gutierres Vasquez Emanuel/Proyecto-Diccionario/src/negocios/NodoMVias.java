/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Emanuel
 * @param <K>
 * @param <V>
 */
public class NodoMVias <K, V>{
    
    private List<K>listaDeClaves;
    private List<V>listaDeValores;
    private List<NodoMVias<K,V>>listaDeHijos;
    
    
    public static NodoMVias nodoVacio(){
        return null;
    }
    
    public static NodoMVias datoVacio(){
        return null;
    }
    
    
    
    public NodoMVias(int grado){
        listaDeClaves=new LinkedList<>();
        listaDeValores=new LinkedList<>();
        listaDeHijos=new LinkedList<>();
        for(int i=0;i<grado-1;i++){
            listaDeClaves.add((K)NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());
        }
        listaDeHijos.add(null);
    }
    
    public NodoMVias(int orden,K primerClave,V primerValor){
        this(orden);
        listaDeClaves.set(0, primerClave);
        listaDeValores.set(0, primerValor);
    }
    
    public NodoMVias<K,V>getHijo(int posicion){
        return listaDeHijos.get(posicion);
    }
    
    public K getClave(int posicion){        
        return listaDeClaves.get(posicion);    
    }
    
    public V getValor(int posicion){
        return listaDeValores.get(posicion);   
    }
    
    public void setClave(int pos,K clave){
        listaDeClaves.set(pos, clave);
    }
    
    public void setValor(int posicion,V clave){ 
        this.listaDeValores.set(posicion,clave);
    }
    
    public void setHijo(int posicion,NodoMVias<K,V>nodo){
        this.listaDeHijos.set(posicion, nodo);
    }
    
    public static boolean esNodoVacio(NodoMVias nodo){
       return nodo==NodoMVias.nodoVacio();
    }
    
    public boolean esClavesVacia(int posicion){
        return this.listaDeClaves.get(posicion)==NodoMVias.datoVacio();
    }
    
    public boolean esHijoVacio(int posicion){
       return  this.listaDeHijos.get(posicion)==NodoMVias.nodoVacio();
    }
    
    public boolean esHoja(){
        for(int i=0;i<listaDeHijos.size();i++){
                if(!esHijoVacio(i)){
                    return false;
                }
        }
       return true; 
     }
    
    public boolean estanClavesLlenas(){
        for(int i=0;i<this.listaDeClaves.size();i++){
            if(this.esClavesVacia(i)){
                return false;
            }
        }
        return true;
    }
    
    public int cantidadDeClavesNoVacias(){
     int cantidad=0;
      for(int i=0;i<this.listaDeClaves.size();i++){
            if(!this.esClavesVacia(i)){
                cantidad++;
            }
        }
      return cantidad;
    }
    
    public int cantidadDeHijosVacios(){
        int cantidad=0;
            for(int i=0;i<this.listaDeHijos.size();i++){
                if(esHijoVacio(i)){
                    cantidad++;
                }
            }
            return cantidad;
    }
    
    public int cantidadDeHijosNoVacios(){
        int cantidad=0;
        for(int i=0;i<listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    } 

    @Override
    public String toString() {
       return this.listaDeClaves.toString();
    }

    
}

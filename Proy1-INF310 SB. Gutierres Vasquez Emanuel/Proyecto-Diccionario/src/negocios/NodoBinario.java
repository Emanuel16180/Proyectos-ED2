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
public class NodoBinario<K,V>{
    private NodoBinario<K, V> hijoIzquierdo;
    private K clave;
    private V valor;
    private NodoBinario<K, V> hijoDerecho;
    
    //Constructor
    public NodoBinario() {
    }
    
    //Constructor parametrizado
    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }
    
    //Constructor de copia
    public NodoBinario(NodoBinario<K, V> nodoCopia) {
        this.hijoIzquierdo = nodoCopia.getHijoIzquierdo();
        this.clave = nodoCopia.getClave();
        this.valor = nodoCopia.getValor();
        this.hijoDerecho = nodoCopia.getHijoDerecho();
    }
    
    //Setters 
    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }
    
    public void setClave(K clave) {
        this.clave = clave;
    }
    
     public void setValor(V valor) {
        this.valor = valor;
    }
    
     public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    //Getters
    public NodoBinario<K, V> getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }
    
    public K getClave() {
        return this.clave;
    }
    
    public V getValor() {
        return this.valor;
    }
    
    public NodoBinario<K, V> getHijoDerecho() {
        return this.hijoDerecho;
    }
    
    //Operaciones extras
    public static NodoBinario nodoVacio(){
        return null;
    }
    
    public static boolean esNodoVacio(NodoBinario unNodo){
        return unNodo == NodoBinario.nodoVacio();
    }
    
    public boolean esHijoIzquierdoVacio(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    
    public boolean esHijoDerechoVacio(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }
    
    public boolean esHoja(){
        return (this.esHijoIzquierdoVacio() && this.esHijoDerechoVacio());
    }  
}

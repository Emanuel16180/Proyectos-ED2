/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import excepciones.ClaveNoExisteException;
import java.util.List;

/**
 *
 * @author Emanuel
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
         void insertar(K clave, V valor);
    V eliminar (K clave) throws ClaveNoExisteException; 
    V buscar (K clave);
    boolean contiene(K clave);
    int size();
    int altura();
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<V> recorridoEnInOrdenV();
    List<K> recorridoEnPostOrden();
}

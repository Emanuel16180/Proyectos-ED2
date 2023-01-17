/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import excepciones.ClaveNoExisteException;

/**
 *
 * @author Emanuel
 */
public class SpeedTest {
    
    public static void main(String[] args) throws ClaveNoExisteException {
        ArbolBinarioBusqueda<Integer,Integer> x = new ArbolBinarioBusqueda();
        x.insertar(1, 5);
        x.insertar(2, 1);
        x.insertar(3, 1);
        x.insertar(4, 3);
        x.insertar(5, 1);
        x.insertar(6, 1);
        x.insertar(7, 7);
        x.insertar(8, 1);
        System.out.println("***** ABB *****");
        System.out.println(x);
        x.eliminar(1);
        System.out.println(x);
        System.out.println(x.recorridoEnInOrden());
        System.out.println(x.recorridoEnInOrdenV());
        System.out.println("");
        AVL<Integer,Integer> y = new AVL<>();
        y.insertar(1, 1);
        y.insertar(2, 4);
        y.insertar(3, 1);
        y.insertar(4, 1);
        y.insertar(5, 3);
        y.insertar(6, 1);
        y.insertar(7, 1);
        y.insertar(8, 10);
        System.out.println("***** AVL *****");
        System.out.println(y);
        y.eliminar(5);
        System.out.println(y);
        System.out.println(y.recorridoEnInOrden());
        System.out.println(y.recorridoEnInOrdenV());
        System.out.println("");
        System.out.println("***** MVias *****");
        ArbolMViasDeBusqueda<Integer,Integer> z= new ArbolMViasDeBusqueda<>();
        z.insertar(4, 11);
        z.insertar(5, 12);
        z.insertar(1, 14);
        z.insertar(2, -1);
        z.insertar(3, 1);
        z.insertar(6, 1);
        z.insertar(7, 1);
        z.insertar(8, 10);
        System.out.println(z);
        System.out.println(z.eliminar(3));
        System.out.println(z);
        System.out.println(z.recorridoEnInOrden());
        System.out.println(z.recorridoEnInOrdenV());
        System.out.println("");  
    }
}

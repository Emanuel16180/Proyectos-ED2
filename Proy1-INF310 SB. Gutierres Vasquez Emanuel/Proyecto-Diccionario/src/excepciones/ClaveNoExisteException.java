/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author Emanuel
 */
public class ClaveNoExisteException extends Exception {

    /**
     * Creates a new instance of <code>ClaveNoExisteException</code> without
     * detail message.
     */
    public ClaveNoExisteException() {
    }

    /**
     * Constructs an instance of <code>ClaveNoExisteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ClaveNoExisteException(String msg) {
        super(msg);
    }
}

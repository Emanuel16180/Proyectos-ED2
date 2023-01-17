package bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.utileria.UtilsRecorrido;

public class BFS {
    private UtilsRecorrido controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;

    public BFS (Grafo unGrafo, int posVerticePartida){
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorrido(this.grafo.cantidadDeVertice()); // ya esta todo desmarcado
        ejecutarBFS(posVerticePartida);

    }

    private void ejecutarBFS(int posVertice){
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        controlMarcados.marcarVertice(posVertice);
        do{
            int posVerticeEnTurno = cola.poll();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
                if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente )){
                    cola.offer(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);

                }
            }

        } while (!cola.isEmpty());

    }

    public void seguirRecorrido(int posVerticeContinua){
        ejecutarBFS(posVerticeContinua);
    }

    public boolean hayCaminoVertice(int posVertice){
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);


    }
    public Iterable<Integer> elRecorrido(){
        return this.recorrido;
    }

    public boolean hayCaminosATodos(){
        return controlMarcados.estanTodosMarcados();
    }

    public boolean estaVerticeMarcado(int posVertice){
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    // Devuelve un vertice no marcado con adyacentes marcados, si no encuentra ninguno retorna -1
    public int buscarVerticeNoMarcadoConAdyacentesMarcados(){
        for (int i = 0; i < grafo.cantidadDeVertice(); i++) {
            if(!estaVerticeMarcado(i)){
                Iterator<Integer> adyacentesDelVerticeEnTurno = this.grafo.adyacentesDeVertice(i).iterator();
                while (adyacentesDelVerticeEnTurno.hasNext()) {
                    if(estaVerticeMarcado(adyacentesDelVerticeEnTurno.next()))
                        return i;
                }
            }
        }
        return -1;
    }

    // Devuelve un vertice no marcado con adyacentes no marcados, si no encuentra ninguno retorna -1
    public int buscarVerticeNoMarcadoConAdyacentesNoMarcados() {
        boolean estanTodosLosAdyacentesNoMarcados = true;
        for (int i = 0; i < grafo.cantidadDeVertice(); i++) {
            if (!estaVerticeMarcado(i)) {
                Iterator<Integer> adyacentesDelVerticeEnTurno = this.grafo.adyacentesDeVertice(i).iterator();
                while (adyacentesDelVerticeEnTurno.hasNext()) {
                    if (estaVerticeMarcado(adyacentesDelVerticeEnTurno.next())) {
                        estanTodosLosAdyacentesNoMarcados = false;
                    }
                }

                if(estanTodosLosAdyacentesNoMarcados){
                    return i;
                } else {
                    estanTodosLosAdyacentesNoMarcados = true;
                }
            }
        }
        return -1;
    }
}

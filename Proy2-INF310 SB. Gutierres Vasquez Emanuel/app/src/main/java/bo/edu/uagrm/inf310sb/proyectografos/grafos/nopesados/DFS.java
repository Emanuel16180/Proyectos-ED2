package bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionNroVerticesInvalidos;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.utileria.UtilsRecorrido;

public class DFS {
    UtilsRecorrido controlMarcados;
    Grafo grafo;
    List<Integer> recorrido;

    public DFS(Grafo unGrafo, int posVerticePartida){
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorrido(this.grafo.cantidadDeVertice()); // ya esta todo desmarcado
        //ejecutarDFS(posVerticePartida);
        ejecutarDFSIterativo(posVerticePartida);

    }

    public DFS(Grafo unGrafo){
        this.grafo = unGrafo;
        //grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorrido(this.grafo.cantidadDeVertice()); // ya esta todo desmarcado
        //ejecutarDFS(posVerticePartida);
        //ejecutarDFSIterativo(posVerticePartida);
    }

    private void ejecutarDFS(int posVertice){
        recorrido.add(posVertice);
        controlMarcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
            if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente )){
                ejecutarDFS(posVerticeAdyacente);

                //podria hacerce con pila
            }
        }
    }

    public void ejecutarDFSIterativo(int posVerticeDePartida){
        Stack<Integer> pilaDeVertices = new Stack<>();
        pilaDeVertices.push(posVerticeDePartida);
        controlMarcados.marcarVertice(posVerticeDePartida);
        do{
            int posVerticeEnTurno = pilaDeVertices.pop();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
                if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente )){
                    pilaDeVertices.push(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);

                }
            }

        } while (!pilaDeVertices.isEmpty());
    }

    public void continuarRecorrido(int posDeVertice){
        this.ejecutarDFS(posDeVertice);
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

    //Verificar para que puedo usarlo mas tarde. xD
    //private int buscarVerticeSinMarcar() {
    //  for(int c=0; c < grafo.ListaDeAdyacencias.size();c++){
    //      if(!controlMarcados.estaVerticeMarcado(c)){
    //          return c;
    //     }
    // }
    //  return grafo.ListaDeAdyacencias.size();
    // }

    public boolean estaVerticeMarcado(int posVertice){
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    public int buscarVerticeNoMarcado(){
        for (int i = 0; i < grafo.cantidadDeVertice(); i++) {
            if(!estaVerticeMarcado(i)){
                return i;
            }
        }
        return -1;
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


    public boolean tieneCiclo() throws ExcepcionNroVerticesInvalidos, ExcepcionAristaYaExiste {
        Grafo grafoAux = new Grafo(grafo.ListaDeAdyacencias.size());
        Stack<Integer> pilaDeVertices = new Stack<>();
        int posVerticeDePartida = 0;
        pilaDeVertices.push(posVerticeDePartida);
        controlMarcados.marcarVertice(posVerticeDePartida);
        while (!controlMarcados.estanTodosMarcados()) {
            while (!pilaDeVertices.isEmpty()) {
                int posVerticeEnTurno = pilaDeVertices.pop();
                recorrido.add(posVerticeEnTurno);
                Iterator<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno).iterator();
                while(adyacentesDeVerticeEnTurno.hasNext()){
                    //for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
                    int posVerticeAdyacente = adyacentesDeVerticeEnTurno.next();
                    if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                        grafoAux.insertarArista(posVerticeEnTurno, posVerticeAdyacente);
                        pilaDeVertices.push(posVerticeAdyacente);
                        controlMarcados.marcarVertice(posVerticeAdyacente);

                    }else{
                        if(!grafoAux.existeAdyacencia(posVerticeEnTurno, posVerticeAdyacente)){
                            return true;
                        }
                    }
                }
            }
            posVerticeDePartida = buscarVerticeNoMarcado();
            if(posVerticeDePartida!=-1){
                pilaDeVertices.push(posVerticeDePartida);
                controlMarcados.marcarVertice(posVerticeDePartida);
            }

        }

        return false;
    }
}

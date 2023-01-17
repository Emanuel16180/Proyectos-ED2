package bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionNroVerticesInvalidos;

public class Grafo{
    protected List<List<Integer>> ListaDeAdyacencias;
    public Grafo() {
        this.ListaDeAdyacencias = new ArrayList<>();
    }

    public Grafo(int nroInicialDelVertice) throws ExcepcionNroVerticesInvalidos {
        if (nroInicialDelVertice <= 0) {
            throw new ExcepcionNroVerticesInvalidos();
        }
        this.ListaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDelVertice; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.ListaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertice() {
        return this.ListaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> listaDeAdyacentesDelVertice = this.ListaDeAdyacencias.get(posDeVertice);
        return listaDeAdyacentesDelVertice.size();
    }

    public void validarVertice(int posDeVertice) {
        int x = this.cantidadDeVertice();
        if (posDeVertice < 0 || posDeVertice >= x)
        {
            throw new IllegalArgumentException("Vertice invalido");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.ListaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.ListaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableAdyacentes = adyacentesDelVertice;
        return iterableAdyacentes;
    }

    /*public int cantidadDeArista() {
        int cantArist = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.ListaDeAdyacencias.get(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantArist++;
                }
            }// fin foreach
        }// fin for
        return cantLazos + (cantArist / 2);
    }*/



    public int cantidadDeArista(){
        int cantidadDeAristas = 0;
        int cantidadDeLazos = 0;
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDelVerticeEnTurno = this.ListaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentesDelVerticeEnTurno.size(); j++) {
                if(i==adyacentesDelVerticeEnTurno.get(j))
                    cantidadDeLazos++;
                else
                    cantidadDeAristas++;
            }
        }
        return cantidadDeAristas/2 + cantidadDeLazos;
    }



    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.ListaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelOrigen.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
        this.ListaDeAdyacencias.remove(posVerticeAEliminar);
        for (List<Integer> adyacentesDeVertice : this.ListaDeAdyacencias){
            int posicionDeVerticeEnAdy= adyacentesDeVertice.indexOf(posVerticeAEliminar);
            if( posicionDeVerticeEnAdy >=0){
                adyacentesDeVertice.remove(posicionDeVerticeEnAdy);
            }
            for (int i =0; i < adyacentesDeVertice.size(); i++){
                int posicionDeAdyacente = adyacentesDeVertice.get(i);
                if(posicionDeAdyacente > posVerticeAEliminar){
                    adyacentesDeVertice.set(i, posicionDeAdyacente - 1);
                }
            }
        }
    }

    public boolean[][] generarMatrizDeAdyacencia(){
        boolean matriz[][] = new boolean[ListaDeAdyacencias.size()][ListaDeAdyacencias.size()];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                List<Integer> adyacentes = this.ListaDeAdyacencias.get(i);
                if(adyacentes.contains(j))
                    matriz[i][j] = true;
                else
                    matriz[i][j] = false;
            }
        }
        return matriz;
    }

    public boolean[][] generarMatrizDeCaminos(){ //Warshall
        boolean matrizDeCaminos[][] = generarMatrizDeAdyacencia();
        for (int k = 0; k < matrizDeCaminos.length; k++) {
            for (int i = 0; i < matrizDeCaminos.length; i++) {
                for (int j = 0; j < matrizDeCaminos.length; j++) {
                    matrizDeCaminos[i][j] = matrizDeCaminos[i][j] || (matrizDeCaminos[i][k] && matrizDeCaminos[k][j]);
                }
            }
        }
        return matrizDeCaminos;
    }

    public boolean esConexo(){
        if(this.cantidadDeVertice() == 0)
            throw new IllegalArgumentException("Grafo Vacio");
        BFS recorridoEnAmplitud = new BFS(this,0);
        return recorridoEnAmplitud.hayCaminosATodos();
    }

    public boolean tieneCiclo() throws ExcepcionNroVerticesInvalidos, ExcepcionAristaYaExiste{
        DFS recorridoEnProfundidad = new DFS(this);
        return recorridoEnProfundidad.tieneCiclo();
    }

    public int cantidadDeIslas(){
        int cantidad = 0;
        if(this.cantidadDeVertice()==0){
            throw new IllegalArgumentException("Grafo Vacio");
        }
        BFS recorrido = new BFS (this,0);
        while(!recorrido.hayCaminosATodos()){
            int posicionDeVerticeNoMarcado=0;
            while(recorrido.estaVerticeMarcado(posicionDeVerticeNoMarcado)){
                posicionDeVerticeNoMarcado++;
            }
            cantidad++;
            recorrido.seguirRecorrido(posicionDeVerticeNoMarcado);
        }
        return cantidad+1;
    }


    @Override
    public String toString() {
        String cadena="";
        for(int i = 0; i < this.ListaDeAdyacencias.size(); i++){
            cadena += i + " -> " + this.ListaDeAdyacencias.get(i).toString() + "\n";
        }
        return cadena;
    }

    public String toStringMatriz(boolean matriz[][]){
        //boolean matriz[][] = generarMatrizDeAdyacencia();
        String cadena = "";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if(matriz[i][j])
                    cadena += 1 + " ";
                else
                    cadena += 0 + " ";

            }
            cadena+="\n";
        }
        return cadena;
    }
}

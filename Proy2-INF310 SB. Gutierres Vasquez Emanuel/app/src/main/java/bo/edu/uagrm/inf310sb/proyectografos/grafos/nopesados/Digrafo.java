package bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionNroVerticesInvalidos;

public class Digrafo extends Grafo {

    public Digrafo(){
        super();
    }

    public Digrafo(int nroInicialDelVertice) throws ExcepcionNroVerticesInvalidos {
        super();
    }

    @Override
    public int cantidadDeArista(){
        int cantidadDeAritas=0;
        for(int i = 0; i < this.ListaDeAdyacencias.size(); i++){
            cantidadDeAritas += this.ListaDeAdyacencias.get(i).size();
        }
        return cantidadDeAritas;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = super.ListaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Metodo no soportado en grafos dirigidos");
    }

    public int gradoDeEntrada(int posVertice){
        this.validarVertice(posVertice);
        int entradasDeVertice = 0;
        for (int i = 0; i < super.ListaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacenteDeUnVertice = super.adyacentesDeVertice(i);
            for(Integer posDeAdyacente : adyacenteDeUnVertice){
                if(posDeAdyacente == posVertice){
                    entradasDeVertice++;
                }
            }

        }
        return entradasDeVertice;
    }

    public int gradoDeSalida(int posDeVertice){
        return super.gradoDeVertice(posDeVertice);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.ListaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
    }

    @Override
    public boolean esConexo(){
        throw new UnsupportedOperationException("Metodo no soportado en grafos dirigidos");
    }

    public boolean esFuertementeConexo(){
        if(this.ListaDeAdyacencias.size() == 0){
            throw new IllegalArgumentException("Grafo dirgido vacio");
        }

        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            BFS recorridoEnAmplitud = new BFS (this,i);
            if(!recorridoEnAmplitud.hayCaminosATodos())
                return false;
        }

        return true;
    }

    public boolean esDebilmenteConexo() throws ExcepcionNroVerticesInvalidos, ExcepcionAristaYaExiste{
        if(this.ListaDeAdyacencias.size() == 0){
            throw new IllegalArgumentException("Grafo dirgido vacio");
        }
        Grafo digrafoConvertidoAGafo = this.crearGrafo();
        return digrafoConvertidoAGafo.esConexo();
    }

    public boolean esDebilmenteConexo2(){
        DFS recorridoEnProdundidad = new DFS(this,0);
        int verticeNoMarcadoConAdyacentesMarcados = recorridoEnProdundidad.buscarVerticeNoMarcadoConAdyacentesMarcados();
        while(verticeNoMarcadoConAdyacentesMarcados != -1){
            recorridoEnProdundidad.continuarRecorrido(verticeNoMarcadoConAdyacentesMarcados);
            verticeNoMarcadoConAdyacentesMarcados = recorridoEnProdundidad.buscarVerticeNoMarcadoConAdyacentesMarcados();
        }
        return recorridoEnProdundidad.hayCaminosATodos();
    }

    public Grafo crearGrafo() throws ExcepcionNroVerticesInvalidos, ExcepcionAristaYaExiste {
        Grafo grafo = new Grafo(this.ListaDeAdyacencias.size());
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDelVerticeEnTurno = this.ListaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentesDelVerticeEnTurno.size(); j++) {
                int adyacenciaDelVertice = adyacentesDelVerticeEnTurno.get(j);
                if (!grafo.existeAdyacencia(i,adyacenciaDelVertice)) {
                    grafo.insertarArista(i, adyacenciaDelVertice);
                }
            }
        }
        return grafo;
    }

    public boolean existeCiclo(){
        boolean matrizDeCamino[][] = super.generarMatrizDeCaminos();
        for (int i = 0; i < matrizDeCamino.length; i++) {
            if(matrizDeCamino[i][i]){
                return true;
            }
        }
        return false;
    }

    public List<Integer> verticesXQueLleganAVerticeY(int verticeALlegar){
        List<Integer> listaDeVerticesQueLlegan = new LinkedList<>();
        //UtilsRecorrido controlDeMarcado = new UtilsRecorrido(this.ListaDeAdyacencias.size());
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            DFS recorridoEnProdundidad = new DFS(this, i);
            if(recorridoEnProdundidad.estaVerticeMarcado(verticeALlegar) && i!=verticeALlegar)
                listaDeVerticesQueLlegan.add(i);
        }
        //int posicionDelVerticeALlegar = listaDeVerticesQueLlegan.indexOf(verticeALlegar);
        //listaDeVerticesQueLlegan.remove(posicionDelVerticeALlegar);
        return listaDeVerticesQueLlegan;
    }

    public boolean esAristaBidireccional(int verticeOrigen, int verticeDestino){
        validarVertice(verticeOrigen);
        validarVertice(verticeDestino);
        return existeAdyacencia(verticeOrigen, verticeDestino) && existeAdyacencia(verticeDestino, verticeOrigen);
    }

    public void invertirAristas() throws ExcepcionAristaNoExiste{
        List<Integer> listaDeTamañoDeLasAdyacencias = new LinkedList<>();
        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            listaDeTamañoDeLasAdyacencias.add(this.ListaDeAdyacencias.get(i).size());
        }

        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            int k = 0;
            for (int j = 0; j < listaDeTamañoDeLasAdyacencias.get(i); j++) {
                int verticeAdyacente = this.ListaDeAdyacencias.get(i).get(k);
                if(!esAristaBidireccional(i, verticeAdyacente)){
                    eliminarArista(i, verticeAdyacente);
                    List<Integer> adyacentesDelOrigen = super.ListaDeAdyacencias.get(verticeAdyacente);
                    adyacentesDelOrigen.add(i);
                }else{
                    k++;
                }
            }
        }

        for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
            Collections.sort(this.ListaDeAdyacencias.get(i));
        }

    }



    @Override
    public int cantidadDeIslas(){
        int cantidad = 0;
        if(this.cantidadDeVertice()==0){
            throw new IllegalArgumentException("Grafo Vacio");
        }
        //boolean hayVerticesNoMarcadosConAdyacentesMarcados = false;
        BFS recorrido = new BFS (this,0);
        //if(recorrido.hayCaminosATodos()){
        //   cantidad++;
        //   return cantidad;
        //}
        while(!recorrido.hayCaminosATodos()){
            //Busco un vertice no marcado con adyacentes marcados
            int posicionDeVerticeNoMarcado=recorrido.buscarVerticeNoMarcadoConAdyacentesMarcados();

            if(posicionDeVerticeNoMarcado == -1){
                posicionDeVerticeNoMarcado = 0;
                while(recorrido.estaVerticeMarcado(posicionDeVerticeNoMarcado)){
                    posicionDeVerticeNoMarcado++;
                }
                cantidad++;
            }

            recorrido.seguirRecorrido(posicionDeVerticeNoMarcado);
            //hayVerticesNoMarcadosConAdyacentesMarcados = false;
        }
        return cantidad+1;
    }





    public List<Integer> verticesXQueLleganAVerticeY2(int verticeALlegar) throws ExcepcionAristaNoExiste{
        List<Integer> listaDeVerticesQueLlegan = new LinkedList<>();
        this.invertirAristas();
        DFS recorridoEnProdundidad = new DFS(this, verticeALlegar);
        this.invertirAristas();
        Iterator<Integer> x = recorridoEnProdundidad.elRecorrido().iterator();
        while(x.hasNext()){
            int vertice = x.next();
            if(vertice!=verticeALlegar){
                listaDeVerticesQueLlegan.add(vertice);
            }
        }
        Collections.sort(listaDeVerticesQueLlegan);
        return listaDeVerticesQueLlegan;
    }



    public List<Integer> ordenamientoTopologico() throws ExcepcionNroVerticesInvalidos, ExcepcionAristaYaExiste{
        List<Integer> ordenamiento = new LinkedList<>();
        if(!existeCiclo() && esDebilmenteConexo()){ // si hay ciclo o no es fuertemente conexo retorna una lista vacia
            List<Integer> listaDeGradoDeEntrada = new LinkedList<>();
            List<Integer> listaDeVertices = new LinkedList<>();
            for (int i = 0; i < this.ListaDeAdyacencias.size(); i++) {
                listaDeGradoDeEntrada.add(gradoDeEntrada(i));
                listaDeVertices.add(i);
            }
            Queue<Integer> colaDeVerticeConGrado0 = new LinkedList<>();
            while(listaDeGradoDeEntrada.contains(0)){
                int ubicacionDelCero = listaDeGradoDeEntrada.indexOf(0);
                colaDeVerticeConGrado0.offer(listaDeVertices.get(ubicacionDelCero));
                listaDeGradoDeEntrada.remove(ubicacionDelCero);
                listaDeVertices.remove(ubicacionDelCero);
            }

            while (!colaDeVerticeConGrado0.isEmpty()) {
                int verticeEnTurno = colaDeVerticeConGrado0.poll();
                for (int i = 0; i < this.ListaDeAdyacencias.get(verticeEnTurno).size(); i++) {
                    int adyacenteEnTurno = this.ListaDeAdyacencias.get(verticeEnTurno).get(i);
                    if(listaDeVertices.contains(adyacenteEnTurno)){
                        int indiceDondeEstaAdyacente = listaDeVertices.indexOf(adyacenteEnTurno);
                        int actualizarGradoEntrada = listaDeGradoDeEntrada.get(indiceDondeEstaAdyacente);
                        actualizarGradoEntrada--;
                        if(actualizarGradoEntrada==0){
                            listaDeGradoDeEntrada.remove(indiceDondeEstaAdyacente);
                            colaDeVerticeConGrado0.add(listaDeVertices.get(indiceDondeEstaAdyacente));
                            listaDeVertices.remove(indiceDondeEstaAdyacente);
                        } else
                            listaDeGradoDeEntrada.set(indiceDondeEstaAdyacente, actualizarGradoEntrada);
                    }
                }
                ordenamiento.add(verticeEnTurno);
            }
        }
        return ordenamiento;
    }

}

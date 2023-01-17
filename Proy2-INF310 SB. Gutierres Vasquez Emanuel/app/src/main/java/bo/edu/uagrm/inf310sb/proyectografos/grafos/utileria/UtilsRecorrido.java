package bo.edu.uagrm.inf310sb.proyectografos.grafos.utileria;

import java.util.ArrayList;
import java.util.List;

public class UtilsRecorrido {
    private List<Boolean> marcados;


    public UtilsRecorrido(int numVertices){
        marcados = new ArrayList<>();
        for (int i = 0 ; i< numVertices; i++){
            marcados.add(Boolean.FALSE);
        }
    }

    public void marcarVertice(int posVertice){
        //pre: La posicion es valida
        marcados.set(posVertice, Boolean.TRUE);
    }

    public boolean estaVerticeMarcado(int posVertice){
        return marcados.get(posVertice);
    }

    public void desmarcarTodos(){
        for (int i = 0 ; i< marcados.size(); i++){
            marcados.set(i, Boolean.FALSE);
        }
    }

    public boolean estanTodosMarcados(){
        for(boolean marcado : marcados){
            if(!marcado){
                return false;
            }
        }
        return true;
    }
}

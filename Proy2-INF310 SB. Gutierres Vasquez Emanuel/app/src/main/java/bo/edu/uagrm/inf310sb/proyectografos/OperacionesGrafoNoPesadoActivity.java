package bo.edu.uagrm.inf310sb.proyectografos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados.Digrafo;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados.Grafo;

public class OperacionesGrafoNoPesadoActivity extends AppCompatActivity {
    TextView prueba;
    String[] info;
    Boolean esGrafo;
    Button b1,b2,b3,b4,b5,b6;
    TextView txtGrafo,txtOperaciones;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_grafo_no_pesado);

        b1 = (Button)findViewById(R.id.button7); // es conexo
        b2 = (Button)findViewById(R.id.button8); // es f.c.
        b3 = (Button)findViewById(R.id.button9); // es d.c.
        b4 = (Button)findViewById(R.id.button10); // hay ciclo
        b5 = (Button)findViewById(R.id.button11); // numero islas
        b6 = (Button)findViewById(R.id.button12); // Ordenamiento Topologico

        txtGrafo = (TextView) findViewById(R.id.textView7);
        txtOperaciones = (TextView) findViewById(R.id.textView8);

        prueba = (TextView) findViewById(R.id.textView6);
        Bundle objetoRecivido = getIntent().getExtras();
        info = objetoRecivido.getStringArray("datos");
        esGrafo = objetoRecivido.getBoolean("esGrafo");
        txtGrafo.setText(info[0]);
        if(esGrafo){
            b2.setEnabled(false);
            b3.setEnabled(false);
            b6.setEnabled(false);
        } else {
            b1.setEnabled(false);
        }
    }


    public void b1(View v){
        //if(esGrafo)
            txtOperaciones.setText(info[1]);
        //else
           // txtOperaciones.setText(info[0]);
    }

    public void b2(View v){
        //if(esGrafo)
        //    txtOperaciones.setText(info[0]);
        //else
            txtOperaciones.setText(info[1]);
    }
    public void b3(View v){
        //if(esGrafo)
        //    txtOperaciones.setText(info[0]);
        //else
            txtOperaciones.setText(info[2]);
    }
    public void b4(View v){
        if(esGrafo)
            txtOperaciones.setText(info[2]);
        else
            txtOperaciones.setText(info[3]);
    }
    public void b5(View v){
        if(esGrafo)
            txtOperaciones.setText(info[3]);
        else
            txtOperaciones.setText(info[4]);
    }
    public void b6(View v){
        //if(esGrafo)
        //    txtOperaciones.setText(info[0]);
        //else
            txtOperaciones.setText(info[5]);
    }


}
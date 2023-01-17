package bo.edu.uagrm.inf310sb.proyectografos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.nio.file.Files;

import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.excepciones.ExcepcionNroVerticesInvalidos;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados.Digrafo;
import bo.edu.uagrm.inf310sb.proyectografos.grafos.nopesados.Grafo;

public class CargarGrafoNoPesadoActivity extends AppCompatActivity {

    private Button btnInsertarVertice, btnInsertarArista, btnEliminar, btnOperaciones;
    private EditText txtVerticeOrigen, txtVerticeDestino;
    private TextView lbSalidaGrafo, lbFlecha;
    private RadioButton rbtnGrafo, rbtnDigrafo;
    public Grafo unGrafo = new Grafo();
    public Digrafo unDigrafo = new Digrafo();
    String[] datos = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_grafo_no_pesado);
        //****** Vincular botones gaaaa
        btnInsertarVertice = (Button)findViewById(R.id.button3);
        btnInsertarArista = (Button)findViewById(R.id.button4);
        btnEliminar = (Button)findViewById(R.id.button5);
        btnOperaciones = (Button)findViewById(R.id.button6);

        rbtnGrafo = (RadioButton) findViewById(R.id.radioButton2);
        rbtnDigrafo = (RadioButton) findViewById(R.id.radioButton);

        lbFlecha = (TextView) findViewById(R.id.textView3);
        lbSalidaGrafo = (TextView) findViewById(R.id.textView5);

        txtVerticeDestino = (EditText) findViewById(R.id.editTextNumber2);
        txtVerticeOrigen = (EditText) findViewById(R.id.editTextNumber);

        //******
        btnInsertarVertice.setEnabled(false);

        //******
        btnOperaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbtnGrafo.isChecked()){
                    datos[0] = unGrafo.toString();
                    datos[1] = String.valueOf(unGrafo.esConexo());
                    try {
                        datos[2] = String.valueOf(unGrafo.tieneCiclo());
                    } catch (ExcepcionNroVerticesInvalidos e) {
                        throw new RuntimeException(e);
                    } catch (ExcepcionAristaYaExiste e) {
                        throw new RuntimeException(e);
                    }

                    datos[3] = String.valueOf(unGrafo.cantidadDeIslas());
                    datos[5] = "Implementar vertices que llegan ";

                } else {
                    datos[0]=unDigrafo.toString();
                    datos[1]= String.valueOf(unDigrafo.esFuertementeConexo());
                    try {
                        datos[2]= String.valueOf(unDigrafo.esDebilmenteConexo());
                    } catch (ExcepcionNroVerticesInvalidos e) {
                        throw new RuntimeException(e);
                    } catch (ExcepcionAristaYaExiste e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        datos[3]= String.valueOf(unDigrafo.tieneCiclo());
                    } catch (ExcepcionNroVerticesInvalidos e) {
                        throw new RuntimeException(e);
                    } catch (ExcepcionAristaYaExiste e) {
                        throw new RuntimeException(e);
                    }
                    datos[4] = String.valueOf(unDigrafo.cantidadDeIslas());
                    try {
                        datos[5] = unDigrafo.ordenamientoTopologico().toString();
                    } catch (ExcepcionNroVerticesInvalidos e) {
                        throw new RuntimeException(e);
                    } catch (ExcepcionAristaYaExiste e) {
                        throw new RuntimeException(e);
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putStringArray("datos",datos);
                Bundle bundle2 = new Bundle();
                bundle.putBoolean("esGrafo",rbtnGrafo.isChecked());
                Intent btnOperaciones = new Intent(CargarGrafoNoPesadoActivity.this,OperacionesGrafoNoPesadoActivity.class);
                btnOperaciones.putExtras(bundle);
                btnOperaciones.putExtras(bundle2);
                startActivity(btnOperaciones);
            }
        });
    }


    public void insertarVertice(View v) {
        if (rbtnGrafo.isChecked()){
            unGrafo.insertarVertice();
            lbSalidaGrafo.setText(unGrafo.toString());
        }else{
            unDigrafo.insertarVertice();
            lbSalidaGrafo.setText(unDigrafo.toString());
        }

    }

    public void insertarArista(View v) throws ExcepcionAristaYaExiste {
        if (rbtnGrafo.isChecked()){
            int origen = Integer.parseInt(txtVerticeOrigen.getText().toString());
            int destino = Integer.parseInt(txtVerticeDestino.getText().toString());
            unGrafo.insertarArista(origen, destino);
            lbSalidaGrafo.setText(unGrafo.toString());
        }else{
            int origen = Integer.parseInt(txtVerticeOrigen.getText().toString());
            int destino = Integer.parseInt(txtVerticeDestino.getText().toString());
            unDigrafo.insertarArista(origen,destino);
            lbSalidaGrafo.setText(unDigrafo.toString());
        }
    }

    public void eliminar(View v) {
        unGrafo = new Grafo();
        unDigrafo = new Digrafo();
        lbSalidaGrafo.setText("");
    }

    public void esDigrafo(View v){
        btnInsertarVertice.setEnabled(true);
        lbFlecha.setText("-->");
    }

    public void esGrafo(View v){
        btnInsertarVertice.setEnabled(true);
        lbFlecha.setText("");
    }

}
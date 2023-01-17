package bo.edu.uagrm.inf310sb.proyectografos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnNoPesado, btnPesado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNoPesado = (Button)findViewById(R.id.button);
        btnPesado = (Button)findViewById(R.id.button2);

        btnNoPesado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnNoPesado = new Intent(MainActivity.this,CargarGrafoNoPesadoActivity.class);
                startActivity(btnNoPesado);
            }
        });

        btnPesado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnPesado = new Intent(MainActivity.this,CargarGrafoPesadoActivity.class);
                startActivity(btnPesado);
            }
        });

    }
}
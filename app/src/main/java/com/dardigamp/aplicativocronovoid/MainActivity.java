package com.dardigamp.aplicativocronovoid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asumimos que tienes los botones btn_consultar_1, btn_consultar_2, ..., btn_consultar_26 en el XML
        // Cada botón tiene un ID único que corresponde al ID de la estación

        // Bucle para asignar los listeners a los botones
        for (int i = 1; i <= 26; i++) {
            // Genera el nombre del ID del botón de forma dinámica
            int buttonId = getResources().getIdentifier("btn_consultar_" + i, "id", getPackageName());
            Button btnConsultar = findViewById(buttonId);

            // Si el botón existe en el layout
            if (btnConsultar != null) {
                final int idEstacion = i; // El ID de la estación es el mismo número del botón

                btnConsultar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Al hacer clic, pasamos el ID de la estación a la siguiente actividad
                        Intent intent = new Intent(MainActivity.this, Consulta_Activity.class);
                        intent.putExtra("idestacion", idEstacion); // Pasa el ID de la estación
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
package com.dardigamp.aplicativocronovoid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Consulta_Activity extends AppCompatActivity {
    TextView txt_info;
    Button btn_volver;
    consultarApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        txt_info = findViewById(R.id.txt_info);
        btn_volver = findViewById(R.id.btn_volver);

        // Obtener el ID de la estación desde los extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("idestacion")) {
            int idEstacion = extras.getInt("idestacion");
            Log.d("Consulta_Activity", "ID de estación recibido: " + idEstacion);
            // Llamar a la API
            api = new consultarApi();
            api.respuesta(idEstacion, new consultarApi.OnHorariosResponseListener() {
                @Override
                public void onResponse(List<ModeloRetorno> horarios) {
                    StringBuilder respuestaBuilder = new StringBuilder();
                    for (ModeloRetorno horario : horarios) {
                        respuestaBuilder.append("ID: ").append(horario.getId()).append("\n")
                                .append("Horario: ").append(horario.getHora()).append("\n\n");
                    }
                    txt_info.setText(respuestaBuilder.toString()); // Mostrar en el TextView
                }

                @Override
                public void onFailure(String error) {
                    muestraToast("Error: " + error);
                }
            }); // Pasa el listener para manejar la respuesta
        } else {
            muestraToast("No se ha recibido un ID válido.");
        }

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Volver a la actividad anterior
            }
        });
    }

    public void muestraToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
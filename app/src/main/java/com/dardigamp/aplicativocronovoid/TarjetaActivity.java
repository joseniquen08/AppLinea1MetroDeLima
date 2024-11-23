package com.dardigamp.aplicativocronovoid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;
import dto.TarjetaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TarjetaActivity extends AppCompatActivity {
    private TextView idTarjetaLbl, nroTarjetaLbl, saldoLbl, tarifaIdLbl, tipoTarifaLbl, descripcionTarifaLbl, montoTarifaLbl, usuarioLbl;
    private Button btnChargeCard, btnTickets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        // Inicializar los TextViews
        idTarjetaLbl = findViewById(R.id.idtarjetalb);
        nroTarjetaLbl = findViewById(R.id.nrotarjetalb);
        saldoLbl = findViewById(R.id.saldolb);
        tarifaIdLbl = findViewById(R.id.idtarifalb);
        tipoTarifaLbl = findViewById(R.id.tipotarifalb);
        descripcionTarifaLbl = findViewById(R.id.descripciontarifalb);
        montoTarifaLbl = findViewById(R.id.montotarifalb);
        usuarioLbl = findViewById(R.id.usuariolb);

        // Obtener el iduser de SharedPreferences o de otro medio
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        int iduser = sharedPreferences.getInt("user_id", -1);  // Asegúrate de guardar el iduser cuando sea necesario
        Toast.makeText(this, "ID de usuario: " + iduser, Toast.LENGTH_SHORT).show();
        String jwt = sharedPreferences.getString("jwt", null);

        if (iduser == -1 || jwt == null) {
            Toast.makeText(this, "Error: Usuario no autenticado o no disponible", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(Utils.getOkHttpClientWithJwt(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiService = retrofit.create(peticiones.class);

        // Llamada a la API usando el iduser
        Call<TarjetaDTO> call = apiService.obtenerTarjeta(iduser);

        call.enqueue(new Callback<TarjetaDTO>() {
            @Override
            public void onResponse(Call<TarjetaDTO> call, Response<TarjetaDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Procesa los datos de la tarjeta
                        TarjetaDTO tarjeta = response.body();
                        actualizarDatosTarjeta(tarjeta);
                    } else {
                        // La respuesta es exitosa pero el cuerpo está vacío
                        Toast.makeText(getApplicationContext(), "No se encontraron datos para esta tarjeta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // La respuesta no fue exitosa
                    Toast.makeText(getApplicationContext(), "Error en la respuesta de la API. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TarjetaDTO> call, Throwable t) {
                // Error de comunicación, como problemas de red
                Toast.makeText(getApplicationContext(), "Fallo en la llamada a la API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Button btnChargeCard = findViewById(R.id.recargarTarjetaButton);
        btnChargeCard.setOnClickListener(view -> startActivity(new Intent(TarjetaActivity.this, RecargarActivity.class)));

        Button btnTickets = findViewById(R.id.VerTickets);
        btnTickets.setOnClickListener(view -> startActivity(new Intent(TarjetaActivity.this, TicketsActivity.class)));
    }

    private void actualizarDatosTarjeta(TarjetaDTO tarjetaDTO) {
        idTarjetaLbl.setText(String.valueOf(tarjetaDTO.getId()));
        nroTarjetaLbl.setText(String.valueOf(tarjetaDTO.getNrotarjeta()));
        saldoLbl.setText(String.valueOf(tarjetaDTO.getSaldo()));

        if (tarjetaDTO.getTarifaDTO() != null) {
            tarifaIdLbl.setText(String.valueOf(tarjetaDTO.getTarifaDTO().getId()));
            tipoTarifaLbl.setText(tarjetaDTO.getTarifaDTO().getTipo());
            descripcionTarifaLbl.setText(tarjetaDTO.getTarifaDTO().getDescripcion());
            montoTarifaLbl.setText(String.valueOf(tarjetaDTO.getTarifaDTO().getMonto()));
        } else {
            tarifaIdLbl.setText("No disponible");
            tipoTarifaLbl.setText("No disponible");
            descripcionTarifaLbl.setText("No disponible");
            montoTarifaLbl.setText("No disponible");
        }

        usuarioLbl.setText("Usuario logueado"); // Cambia según sea necesario
    }
}
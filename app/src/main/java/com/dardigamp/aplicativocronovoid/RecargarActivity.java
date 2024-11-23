package com.dardigamp.aplicativocronovoid;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;

import dto.RecargaRequest;
import dto.RedirectResponse;
import dto.TarjetaDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecargarActivity extends AppCompatActivity {

    private EditText saldoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recargar);

        saldoInput = findViewById(R.id.saldoInput);
        Button btnCharge = findViewById(R.id.recargarButton);

        btnCharge.setOnClickListener(view -> {
            String saldoStr = saldoInput.getText().toString();
            if (saldoStr.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un monto válido", Toast.LENGTH_SHORT).show();
                return;
            }

            float amount = Float.parseFloat(saldoStr);
            int iduser = Utils.obtenerIdUsuario(this); // Método para obtener el iduser desde SharedPreferences

            if (iduser != -1) {
                obtenerTarjetaYRecargar(iduser, amount);
            } else {
                Toast.makeText(this, "Error al obtener el ID de usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerTarjetaYRecargar(int iduser, float amount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(Utils.getOkHttpClientWithJwt(this)) // Cliente con JWT en cabeceras
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiService = retrofit.create(peticiones.class);

        // Llamada para obtener la tarjeta
        apiService.obtenerTarjeta(iduser).enqueue(new Callback<TarjetaDTO>() {
            @Override
            public void onResponse(Call<TarjetaDTO> call, Response<TarjetaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int idtarjeta = response.body().getId(); // Obtén el ID de la tarjeta
                    iniciarPago(iduser, amount, idtarjeta); // Llama a iniciarPago con idtarjeta
                } else {
                    Toast.makeText(RecargarActivity.this, "Error al obtener la tarjeta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TarjetaDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RecargarActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciarPago(int iduser, float amount, int idtarjeta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(Utils.getOkHttpClientWithJwt(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiService = retrofit.create(peticiones.class);
        RecargaRequest request = new RecargaRequest(amount, iduser, idtarjeta);

        Call<RedirectResponse> call = apiService.createAndRedirect(request);

        call.enqueue(new Callback<RedirectResponse>() {
            @Override
            public void onResponse(Call<RedirectResponse> call, Response<RedirectResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    abrirEnlaceEnCustomTabs(response.body().getSandboxInitPoint());
                } else {
                    Toast.makeText(RecargarActivity.this, "Error al iniciar la recarga", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RedirectResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RecargarActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirEnlaceEnCustomTabs(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setStartAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}


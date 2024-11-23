package com.dardigamp.aplicativocronovoid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;

import dto.EmailRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificacionesActivity extends AppCompatActivity {

    private EditText etRecommendation;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        etRecommendation = findViewById(R.id.etRecommendation);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRecommendation();
            }
        });
    }

    private void sendRecommendation() {
        String message = etRecommendation.getText().toString();

        if (message.isEmpty()) {
            Toast.makeText(this, "Por favor, escribe una recomendación.", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/") // Cambia por la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiService = retrofit.create(peticiones.class);
        EmailRequest emailRequest = new EmailRequest("utpmovilesserver@gmail.com", message);

        Call<String> call = apiService.enviarMensajeSugerencia(emailRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                etRecommendation.setText("");
                if (response.isSuccessful()) {
                    Toast.makeText(NotificacionesActivity.this, "Recomendación enviada con éxito.", Toast.LENGTH_SHORT).show();
                    etRecommendation.setText("");
                } else {
                    Toast.makeText(NotificacionesActivity.this, "Error al enviar. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                etRecommendation.setText("");
                Toast.makeText(NotificacionesActivity.this, "Recomendación enviada con éxito.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
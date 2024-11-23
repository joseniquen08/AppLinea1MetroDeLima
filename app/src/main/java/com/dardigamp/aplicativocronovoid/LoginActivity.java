package com.dardigamp.aplicativocronovoid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;
import dto.AuthLoginRequest;
import dto.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText edUsername, edPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.textViewNewUser);

        btnLogin.setOnClickListener(view -> {
            String username = edUsername.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Por favor complete todos los detalles", Toast.LENGTH_SHORT).show();
            } else {
                iniciarSesion(username, password);
            }
        });

        tvRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void iniciarSesion(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/") // Cambia la URL según tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiClient = retrofit.create(peticiones.class);

        AuthLoginRequest loginRequest = new AuthLoginRequest(username, password);

        apiClient.iniciarSesion(loginRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();

                    if (authResponse.isStatus()) {
                        // Guardar JWT y ID de usuario
                        guardarCredenciales(authResponse);

                        // Navegar a la pantalla principal
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error de autenticación: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCredenciales(AuthResponse authResponse) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("jwt", authResponse.getJwt()); // Guardar el JWT
        editor.putInt("user_id", authResponse.getId()); // Guardar el ID de usuario
        editor.apply(); // Aplicar los cambios

        // Confirmación de almacenamiento
        Toast.makeText(getApplicationContext(),
                "Credenciales guardadas: Usuario ID " + authResponse.getId(),
                Toast.LENGTH_SHORT).show();
    }
}
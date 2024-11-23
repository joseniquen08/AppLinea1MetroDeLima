package com.dardigamp.aplicativocronovoid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;
import dto.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.editTextRegUsername);
        final EditText contrasena = findViewById(R.id.editTextRegPassword);
        final EditText tarjetaInput = findViewById(R.id.editTextRegCardNumber);
        final EditText nombre = findViewById(R.id.editTextRegFirstName);
        final EditText apellido = findViewById(R.id.editTextRegLastName);
        final EditText dni = findViewById(R.id.editTextRegDNI);
        final EditText edad = findViewById(R.id.editTextRegAge);

        Button createAccountButton = findViewById(R.id.buttonRegister);
        createAccountButton.setOnClickListener(view -> {

            String usernameStr = username.getText().toString();
            String contrasenaStr = contrasena.getText().toString();
            String tarjetaStr = tarjetaInput.getText().toString();
            String nombreStr = nombre.getText().toString();
            String apellidoStr = apellido.getText().toString();
            String dniStr = dni.getText().toString();
            String edadStr = edad.getText().toString();

            if (usernameStr.isEmpty() || contrasenaStr.isEmpty() || tarjetaStr.isEmpty() || nombreStr.isEmpty()
                    || apellidoStr.isEmpty() || dniStr.isEmpty() || edadStr.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isNumeric(tarjetaStr) || !isNumeric(edadStr) || !isNumeric(dniStr)) {
                Toast.makeText(RegisterActivity.this, "Error: Verifica los campos numéricos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Crear el objeto UsuarioCreateDTO
                UsuarioCreateDTO usuario = new UsuarioCreateDTO();
                usuario.setUsername(usernameStr);
                usuario.setPassword(contrasenaStr);

                // Crear el objeto TarjetaDTO y asignarlo al Usuario
                TarjetaDTO nuevaTarjeta = new TarjetaDTO(Integer.parseInt(tarjetaStr));
                usuario.setTarjetaDTO(nuevaTarjeta);

                // Crear el objeto PersonaDTO y asignarlo al Usuario
                PersonaDTO nuevaPersona = new PersonaDTO();
                nuevaPersona.setNombre(nombreStr);
                nuevaPersona.setApellido(apellidoStr);
                nuevaPersona.setDni(Integer.parseInt(dniStr));
                nuevaPersona.setEdad(Integer.parseInt(edadStr));
                usuario.setPersonaDTO(nuevaPersona);

                // Enviar la solicitud de red
                sendNetworkRequest(usuario);

            } catch (NumberFormatException e) {
                Toast.makeText(RegisterActivity.this, "Error: Verifica los campos numéricos", Toast.LENGTH_SHORT).show();
            }
        });

        TextView existeusuario = findViewById(R.id.textViewExistingUser);
        existeusuario.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void sendNetworkRequest(UsuarioCreateDTO usuario) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones client = retrofit.create(peticiones.class);
        Call<UsuarioDTO> call = client.crearUsuario(usuario);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Usuario creado con éxito: " + response.body().getUsername(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en la creación: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para verificar si una cadena es numérica
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
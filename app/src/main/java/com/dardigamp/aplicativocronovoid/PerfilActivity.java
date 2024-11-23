package com.dardigamp.aplicativocronovoid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;

import dto.TarjetaDTO;
import dto.UsuarioCreateDTO;
import dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {

    private TextView nombreusr; // Solo para mostrar datos
    private EditText Updateuser, Updatepassword, Updatetarjeta; // Para editar datos
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar las vistas
        nombreusr = findViewById(R.id.nombreusr);
        Updateuser = findViewById(R.id.Updateuser);
        Updatepassword = findViewById(R.id.Updatepassword);
        Updatetarjeta = findViewById(R.id.Updatetarjeta);
        guardar = findViewById(R.id.guardar);  // Asegúrate de que este sea el id correcto

        // Obtener user_id y JWT desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        String jwt = sharedPreferences.getString("jwt", null);

        if (userId == -1 || jwt == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(Utils.getOkHttpClientWithJwt(this)) // Asegúrate de que el cliente OkHttp con JWT esté bien configurado
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peticiones apiService = retrofit.create(peticiones.class);

        // Obtener los datos del usuario con el userId
        Call<UsuarioDTO> call = apiService.obtenerusuario(userId);
        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mostrar los datos del usuario en los campos
                    UsuarioDTO usuario = response.body();
                    nombreusr.setText(usuario.getPersonaDTO().getNombre());  // Nombre solo se muestra
                    Updateuser.setText(usuario.getUsername());  // EditText para usuario
                    Updatetarjeta.setText(String.valueOf(usuario.getTarjetaDTO().getNrotarjeta()));  // EditText para tarjeta
                } else {
                    Toast.makeText(PerfilActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "Error en la conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el listener del botón "Guardar"
        guardar.setOnClickListener(v -> {
            // Validar los datos ingresados

            String usuariopl = Updateuser.getText().toString();
            String passwordpl = Updatepassword.getText().toString();
            String tarjetaInput = Updatetarjeta.getText().toString();

            if (usuariopl.isEmpty() || passwordpl.isEmpty()) {
                Toast.makeText(PerfilActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            int tarjetapl;
            try {
                tarjetapl = Integer.parseInt(tarjetaInput);
            } catch (NumberFormatException e) {
                Toast.makeText(PerfilActivity.this, "Número de tarjeta no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear un objeto Usuario con los nuevos datos
            UsuarioCreateDTO usuario = new UsuarioCreateDTO();
            usuario.setUsername(usuariopl); // El nombre del usuario no se puede editar, solo mostrarlo
            usuario.setPassword(passwordpl);
            usuario.setId(userId);

            TarjetaDTO tarjeta = new TarjetaDTO();
            tarjeta.setNrotarjeta(tarjetapl);
            usuario.setTarjetaDTO(tarjeta);

            // Llamar a la API para actualizar el perfil
            Call<UsuarioCreateDTO> actualizarCall = apiService.actualizarUsuario(usuario);
            actualizarCall.enqueue(new Callback<UsuarioCreateDTO>() {
                @Override
                public void onResponse(Call<UsuarioCreateDTO> call, Response<UsuarioCreateDTO> response) {
                    if (response.isSuccessful()) {
                        // Si la respuesta es exitosa, mostramos un mensaje de éxito
                        Toast.makeText(PerfilActivity.this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si hay un error, mostramos un mensaje de error
                        String errorMsg = "Error al actualizar perfil: " + response.code() + " " + response.message();
                        Toast.makeText(PerfilActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsuarioCreateDTO> call, Throwable t) {
                    // Si la solicitud falla (problema de red), mostramos un mensaje
                    Toast.makeText(PerfilActivity.this, "Error en la conexión con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
package com.dardigamp.aplicativocronovoid;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;
import com.dardigamp.aplicativocronovoid.model.Horario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.HorarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class consultarApi {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/v1/";
    private Retrofit varRetro;

    public consultarApi() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .create();

        varRetro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void respuesta(Integer id, final OnHorariosResponseListener listener) {
        peticiones consApi = varRetro.create(peticiones.class);
        Call<List<HorarioDTO>> call = consApi.consultar(id);

        call.enqueue(new Callback<List<HorarioDTO>>() {
            @Override
            public void onResponse(Call<List<HorarioDTO>> call, Response<List<HorarioDTO>> response) {
                if (response.code() == 302) {
                    System.out.println("Redirección a: " + response.headers().get("Location"));
                }
                List<ModeloRetorno> modeloRetornoList = new ArrayList<>();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        List<HorarioDTO> horarios = response.body();
                        for (HorarioDTO horario : horarios) {
                            // Crear instancia de ModeloRetorno sin el campo Estacion
                            ModeloRetorno modelo = new ModeloRetorno();
                            modelo.setId(horario.getId());
                            modelo.setHora(horario.getHora().toString()); // Convertir LocalDateTime a String
                            modeloRetornoList.add(modelo);
                        }
                        listener.onResponse(modeloRetornoList); // Llama al listener con la lista
                    } else {
                        System.out.println("Error en la respuesta: " + response.code());
                        listener.onFailure("Error en la respuesta: " + response.code());
                    }
                } catch (Exception ex) {
                    System.out.println("Error al procesar la respuesta: " + ex.getMessage());
                    listener.onFailure("Error al procesar la respuesta: " + ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<HorarioDTO>> call, Throwable t) {
                System.out.println("Error en la llamada: " + t.getMessage());
                listener.onFailure("Error en la llamada: " + t.getMessage());
            }
        });
    }

    // Interfaz para manejar la respuesta
    public interface OnHorariosResponseListener {
        void onResponse(List<ModeloRetorno> modeloRetornoList);
        void onFailure(String error);
    }
}
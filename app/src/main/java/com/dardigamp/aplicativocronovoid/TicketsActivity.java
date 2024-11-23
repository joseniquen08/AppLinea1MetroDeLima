package com.dardigamp.aplicativocronovoid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dardigamp.aplicativocronovoid.model.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dardigamp.aplicativocronovoid.interfaces.peticiones;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.TarjetaDTO;
import dto.TicketDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketsActivity extends AppCompatActivity {

    private LinearLayout ticketsContainer;
    private List<Ticket> ticketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        ticketsContainer = findViewById(R.id.ticketsContainer);

        // Obtener user_id y JWT desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        String jwt = sharedPreferences.getString("jwt", null);

        if (userId == -1 || jwt == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurar Gson con el adaptador de fechas
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .create();

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .client(Utils.getOkHttpClientWithJwt(this)) // Incluir JWT en las solicitudes
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        peticiones apiService = retrofit.create(peticiones.class);

        // Paso 1: Obtener ID de tarjeta relacionado con el usuario logueado
        obtenerIdTarjetaPorUsuario(apiService, userId);
    }

    private void obtenerIdTarjetaPorUsuario(peticiones apiService, int userId) {
        Call<TarjetaDTO> call = apiService.obtenerTarjeta(userId);
        call.enqueue(new Callback<TarjetaDTO>() {
            @Override
            public void onResponse(Call<TarjetaDTO> call, Response<TarjetaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int idTarjeta = response.body().getId();
                    Log.d("TicketsActivity", "ID de tarjeta obtenido: " + idTarjeta);

                    // Paso 2: Usar el ID de tarjeta para obtener tickets
                    obtenerTicketsPorTarjeta(apiService, idTarjeta);
                } else {
                    Log.e("TicketsActivity", "Error al obtener el ID de la tarjeta: " + response.code());
                    Toast.makeText(TicketsActivity.this, "No se pudo obtener la tarjeta del usuario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TarjetaDTO> call, Throwable t) {
                Log.e("TicketsActivity", "Error al conectar con la API para obtener la tarjeta.", t);
                Toast.makeText(TicketsActivity.this, "Error al conectar con la API.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerTicketsPorTarjeta(peticiones apiService, int idTarjeta) {
        Call<List<TicketDTO>> call = apiService.getTicketsPorTarjeta(idTarjeta);
        call.enqueue(new Callback<List<TicketDTO>>() {
            @Override
            public void onResponse(Call<List<TicketDTO>> call, Response<List<TicketDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ticketList = new ArrayList<>();
                    for (TicketDTO dto : response.body()) {
                        ticketList.add(convertirDtoATicket(dto));
                    }
                    agregarTickets();
                } else {
                    Log.e("TicketsActivity", "Error en la respuesta de la API al obtener tickets: " + response.code());
                    Toast.makeText(TicketsActivity.this, "No se encontraron tickets para esta tarjeta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TicketDTO>> call, Throwable t) {
                Log.e("TicketsActivity", "Error al conectar con la API para obtener tickets.", t);
                Toast.makeText(TicketsActivity.this, "Error al conectar con la API.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Ticket convertirDtoATicket(TicketDTO ticketDTO) {
        return new Ticket(
                ticketDTO.getId(),
                ticketDTO.getFecha(),
                ticketDTO.getSaldoanterior(),
                ticketDTO.getImporte(),
                ticketDTO.getNuevosaldo(),
                null
        );
    }

    private void agregarTickets() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ticketsContainer.removeAllViews();

        if (ticketList.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("No hay tickets disponibles");
            emptyMessage.setTextSize(16);
            emptyMessage.setTextColor(getResources().getColor(android.R.color.black));
            ticketsContainer.addView(emptyMessage);
            return;
        }

        for (Ticket ticket : ticketList) {
            View ticketView = inflater.inflate(R.layout.item_ticket, ticketsContainer, false);

            TextView tvImporte = ticketView.findViewById(R.id.tvImporte);
            TextView tvNuevoSaldo = ticketView.findViewById(R.id.tvNuevoSaldo);
            TextView tvSaldoAnterior = ticketView.findViewById(R.id.tvSaldoAnterior);

            tvImporte.setText("Importe: " + ticket.getImporte());
            tvNuevoSaldo.setText("Nuevo Saldo: " + ticket.getNuevosaldo());
            tvSaldoAnterior.setText("Saldo Anterior: " + ticket.getSaldoanterior());

            ticketsContainer.addView(ticketView);
        }
    }
}
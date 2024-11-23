package com.dardigamp.aplicativocronovoid.interfaces;

import dto.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.dardigamp.aplicativocronovoid.model.Horario;
import com.dardigamp.aplicativocronovoid.model.Usuario;

import java.util.List;

public interface peticiones {
    @GET("horarios/{idestacion}")
    Call<List<HorarioDTO>> consultar(@Path("idestacion") Integer idestacion);

    @POST("create")
    Call<UsuarioDTO> crearUsuario(@Body UsuarioCreateDTO nuevoUsuario);

    @POST("login") // El endpoint para iniciar sesión
    Call<AuthResponse> iniciarSesion(@Body AuthLoginRequest authLoginRequest);

    @GET("auth/tarjeta/{iduser}")
    Call<TarjetaDTO> obtenerTarjeta(@Path("iduser") int iduser);

    @GET("auth/user/{iduser}")
    Call<UsuarioDTO> obtenerusuario(@Path("iduser") int iduser);

    @PUT("auth/update")
    Call <UsuarioCreateDTO> actualizarUsuario (@Body UsuarioCreateDTO usuarioactualizado);

    @POST("auth/createAndRedirect")
    Call<RedirectResponse> createAndRedirect(@Body RecargaRequest recargaRequest);

    @GET("auth/tickets/{idtarjeta}")
    Call<List<TicketDTO>> getTicketsPorTarjeta(@Path("idtarjeta") int idtarjeta);

    @POST("send")
    Call<String> enviarMensajeSugerencia(@Body EmailRequest email);

}


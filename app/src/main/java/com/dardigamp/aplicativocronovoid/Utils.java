package com.dardigamp.aplicativocronovoid;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Utils {
    public static OkHttpClient getOkHttpClientWithJwt(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    String jwt = sharedPreferences.getString("jwt", null);

                    if (jwt != null) {
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer " + jwt);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                    return chain.proceed(original);
                })
                .build();
    }
    public static int obtenerIdUsuario(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // Devuelve -1 si no existe
    }
}
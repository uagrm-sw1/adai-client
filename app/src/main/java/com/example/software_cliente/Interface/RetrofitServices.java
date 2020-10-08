package com.example.software_cliente.Interface;

import com.example.software_cliente.Response.Student;
import com.example.software_cliente.Response.Tutor;
import com.example.software_cliente.Response.User;
import com.example.software_cliente.Response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitServices {

    @POST("login/")
    Call<LoginResponse> login (@Body User user);

    @POST("perfil/")
    Call<Tutor> register (@Body Tutor tutor);

    @POST("estudiante/")
    Call<Student> registerStudent (@Header ("Authorization") String token, @Body Student student);

    @GET("estudiante/")
    Call<List<Student>> getStudents (@Header ("Authorization") String token);

    @PUT("estudiante/{id}/")
    Call<Student> editStudent (@Header ("Authorization") String token, @Path ("id") int id, @Body Student student);

    @DELETE("estudiante/{id}/")
    Call<Student> deleteStudent (@Header ("Authorization") String token, @Path ("id") int id);
}

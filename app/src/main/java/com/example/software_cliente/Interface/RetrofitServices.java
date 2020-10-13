package com.example.software_cliente.Interface;

import com.example.software_cliente.Response.Answer;
import com.example.software_cliente.Response.Exam;
import com.example.software_cliente.Response.ExerciseExam;
import com.example.software_cliente.Response.Exercise;
import com.example.software_cliente.Response.InitialExam;
import com.example.software_cliente.Response.Lesson;
import com.example.software_cliente.Response.Note;
import com.example.software_cliente.Response.Student;
import com.example.software_cliente.Response.Text;
import com.example.software_cliente.Response.Tutor;
import com.example.software_cliente.Response.User;
import com.example.software_cliente.Response.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("ejericicio_examen_inicial/")
    Call<List<InitialExam>> getInitialExamExercise ();

    @GET("ejercicio/{id}/")
    Call<Exercise> getExercise (@Path ("id") int id);

    @GET("respuesta/")
    Call<List<Answer>> getAnswer ();

    @GET("tema/")
    Call<List<Lesson>> getLessons ();

    @POST("nota/")
    Call<Note> createNote (@Body Note note);

    @GET("nota/")
    Call<List<Note>> getNotes ();

    @GET("examen/")
    Call<List<Exam>> getExams ();

    @GET("ejericicio_examen/")
    Call<List<ExerciseExam>> getExerciseExam ();

    @Multipart
    @POST("send-image")
    Call<Text> sendImage (@Part MultipartBody.Part image);
}

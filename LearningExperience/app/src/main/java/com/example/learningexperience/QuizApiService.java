package com.example.learningexperience;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizApiService {
    @GET("api.php?amount=5&type=multiple")
    Call<QuizResponse> getQuizQuestions(@Query("amount") int amount);
}

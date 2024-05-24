package com.example.chatbot;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import okhttp3.RequestBody;

public interface ChatbotApi {
    @POST("chat")
    Call<JSONObject> getChatResponse(@Body RequestBody body);
}

package com.example.chatbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private EditText messageInput;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    private ChatbotApi chatbotApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageInput = findViewById(R.id.messageInput);
        recyclerView = findViewById(R.id.recyclerView);
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/") // Change to your server URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotApi = retrofit.create(ChatbotApi.class);

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            Message userMessage = new Message("User", messageText);
            messageList.add(userMessage);
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
            messageInput.setText("");

            JSONObject requestData = createRequestJson(messageText, messageList);
            printJsonObject(requestData);

            String requestBody = requestData.toString();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

            // Send user message to the Flask API
            Call<JSONObject> call = chatbotApi.getChatResponse(body);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Log the response
                        Log.d("ChatActivity", "Response received: " + response.body().toString());

                        // Convert JSON response to string
                        JSONObject jsonResponse = response.body();
                        printJsonObject(jsonResponse);
                        String responseString = convertResponseToString(jsonResponse);
                        Log.d("ChatActivity", "Response received: " + responseString);

                        // Add llama's response to the list and notify adapter
                        Message llamaMessage = new Message("Llama", responseString);
                        messageList.add(llamaMessage);
                        chatAdapter.notifyItemInserted(messageList.size() - 1);
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    } else {
                        Log.e("ChatActivity", "Failed to get chat response");
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    Log.e("ChatActivity", "Error occurred while fetching chat response", t);
                }
            });
        }
    }

    private JSONObject createRequestJson(String userMessage, List<Message> chatHistory) {
        JSONObject requestData = new JSONObject();
        JSONArray chatHistoryArray = new JSONArray();

        try {
            // Add user message
            requestData.put("userMessage", userMessage);

            // Convert chat history to JSON array
            for (Message message : chatHistory) {
                JSONObject historyItem = new JSONObject();
                historyItem.put("User", message.getUser() != null ? message.getUser() : JSONObject.NULL);
                historyItem.put("Llama", message.getLlama() != null ? message.getLlama() : JSONObject.NULL);
                chatHistoryArray.put(historyItem);
            }

            // Add chat history to request data
            requestData.put("chatHistory", chatHistoryArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestData;
    }

    private void printJsonObject(JSONObject jsonObject) {
        Log.d("JSON Request/Response", jsonObject.toString());
    }

    private String convertResponseToString(JSONObject jsonResponse) {
        try {
            return jsonResponse.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}

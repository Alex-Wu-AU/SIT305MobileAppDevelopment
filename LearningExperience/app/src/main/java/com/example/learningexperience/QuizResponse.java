package com.example.learningexperience;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizResponse {
    @SerializedName("response_code")
    private int responseCode;
    @SerializedName("results")
    private List<QuizQuestion> results;

    // Getters and setters...

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<QuizQuestion> getResults() {
        return results;
    }

    public void setResults(List<QuizQuestion> results) {
        this.results = results;
    }
}


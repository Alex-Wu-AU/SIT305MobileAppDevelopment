package com.example.chatbot;

public class Message {
    private String user;
    private String llama;

    public Message(String user, String llama) {
        this.user = user;
        this.llama = llama;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLlama() {
        return llama;
    }

    public void setLlama(String llama) {
        this.llama = llama;
    }
}

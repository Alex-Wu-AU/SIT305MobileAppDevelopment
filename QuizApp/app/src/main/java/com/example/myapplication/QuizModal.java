package com.example.myapplication;

import java.util.Random;

public class QuizModal {
    private  String question;
    private  String option1;
    private  String option2;
    private  String option3;
    private  String option4;
    private  String answer;

    public QuizModal(String question, String option1, String option2, String option3, String option4, String answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Random questions generated by AI
    public static QuizModal generateRandomQuestion() {
        // Generate a random question and options
        String[] questions = {
                "What is the capital of France?",
                "Which planet is known as the Red Planet?",
                "Who wrote 'To Kill a Mockingbird'?",
                "What is the chemical symbol for water?",
                "What year did the Titanic sink?",
                "Which country is known as the Land of the Rising Sun?",
                "Who painted the Mona Lisa?",
                "What is the tallest mammal?",
                "Which is the largest ocean on Earth?",
                "What is the currency of Japan?",
                "Who invented the telephone?",
                "Which is the largest organ in the human body?",
                "Who wrote the play 'Romeo and Juliet'?",
                "What is the square root of 64?",
                "Which gas is most abundant in Earth's atmosphere?",
                "What is the smallest prime number?",
                "Who discovered penicillin?",
                "What is the boiling point of water in Celsius?",
                "Which country is known as the Land of the Thunder Dragon?",
                "What is the capital of Australia?"
        };

        String[][] options = {
                {"Paris", "London", "Berlin", "Rome"},
                {"Mars", "Venus", "Jupiter", "Saturn"},
                {"Harper Lee", "Stephen King", "J.K. Rowling", "George Orwell"},
                {"H2O", "CO2", "NaCl", "O2"},
                {"1912", "1908", "1915", "1920"},
                {"Japan", "China", "India", "South Korea"},
                {"Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Michelangelo"},
                {"Giraffe", "Elephant", "Horse", "Whale"},
                {"Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"},
                {"Yen", "Dollar", "Euro", "Pound"},
                {"Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "James Watt"},
                {"Skin", "Heart", "Brain", "Liver"},
                {"William Shakespeare", "Jane Austen", "Charles Dickens", "Mark Twain"},
                {"8", "6", "10", "12"},
                {"Nitrogen", "Oxygen", "Carbon dioxide", "Argon"},
                {"2", "3", "5", "7"},
                {"Alexander Fleming", "Marie Curie", "Isaac Newton", "Albert Einstein"},
                {"100°C", "0°C", "50°C", "200°C"},
                {"Bhutan", "Nepal", "Tibet", "Mongolia"},
                {"Canberra", "Sydney", "Melbourne", "Brisbane"}
        };

        Random random = new Random();
        int index = random.nextInt(questions.length);

        // Randomly shuffle the options
        String[] shuffledOptions = options[index].clone();
        for (int i = shuffledOptions.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = shuffledOptions[i];
            shuffledOptions[i] = shuffledOptions[j];
            shuffledOptions[j] = temp;
        }

        // Randomly choose the correct answer
        String correctAnswer = options[index][0];

        return new QuizModal(questions[index], shuffledOptions[0], shuffledOptions[1], shuffledOptions[2], shuffledOptions[3], correctAnswer);
    }

}

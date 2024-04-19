package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    // Create variables for the app to use
    private TextView questionTV, questionNumberTV;
    private Button option1Btn, option2Btn, option3Btn, option4Btn, submitBtn, buttonStartQuiz;
    private ArrayList<QuizModal> quizModalArrayList;
    private EditText editTextName;
    Random random;
    int currentScore = 0, questionAttempted = 1, currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialization of variables
        questionTV = findViewById(R.id.idTVQuestion);
        questionNumberTV = findViewById(R.id.idProgressTV);
        option1Btn = findViewById(R.id.idBtnOption1);
        option2Btn = findViewById(R.id.idBtnOption2);
        option3Btn = findViewById(R.id.idBtnOption3);
        option4Btn = findViewById(R.id.idBtnOption4);
        submitBtn = findViewById(R.id.idBtnSubmit);
        quizModalArrayList = new ArrayList<>();
        random = new Random();
        getQuizQuestion(quizModalArrayList);
        currentPos = random.nextInt(quizModalArrayList.size());
        setDataToViews(currentPos);

        // Asking for the user's name

        ProgressBar progressBar = findViewById(R.id.idProgressBar);
        // Update the ProgressBar based on the number of questions attempted
        progressBar.setProgress(questionAttempted * 20);

        resetButtonColors();

        // Click on the submit button to move on
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionAttempted++;
                currentPos = random.nextInt(quizModalArrayList.size());
                setDataToViews(currentPos);
            }
        });


        // The button click behavior
        option1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtonColors();
                option1Btn.setBackgroundColor(Color.parseColor("#7DD2F4")); // Blue
            }
        });

        option2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtonColors();
                option2Btn.setBackgroundColor(Color.parseColor("#7DD2F4")); // Blue
            }
        });

        option3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtonColors();
                option3Btn.setBackgroundColor(Color.parseColor("#7DD2F4")); // Blue
            }
        });

        option4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtonColors();
                option4Btn.setBackgroundColor(Color.parseColor("#7DD2F4")); // Blue
            }
        });



    }

    private void initializeLandingPage() {
        editTextName = findViewById(R.id.editTextName);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                if (!name.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("userName", name);
                    startActivity(intent);
                    finish();
                } else {
                    // Show a message to enter a name
                    // For example, you can display a Toast message
                }
            }
        });
    }
    private void resetButtonColors() {
        option1Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option2Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option3Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option4Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
    }

        // Show the final results view
        private void showBottomSheet(){
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.idLLScore));
            TextView scoreTV = bottomSheetView.findViewById(R.id.idTVScore);
            Button restartQuizBtn = bottomSheetView.findViewById(R.id.idBtnRestart);
            scoreTV.setText("Your Score is \n" + currentScore + "/5");

            // Restart button set back to the starting of the quiz
            restartQuizBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                    currentPos = random.nextInt(quizModalArrayList.size());
                    setDataToViews(currentPos);
                    questionAttempted = 1;
                    currentScore = 0;
                }
            });
            bottomSheetDialog.setCancelable(false);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }

        // Pass data to views based on current position
        private void setDataToViews(int currentPos){
            questionNumberTV.setText("Question Attempted: " + questionAttempted + "/5");

            // Set the maximum number of quiz
            if (questionAttempted == 5){
                showBottomSheet();
            }
            else{
                questionTV.setText(quizModalArrayList.get(currentPos).getQuestion());
                option1Btn.setText(quizModalArrayList.get(currentPos).getOption1());
                option2Btn.setText(quizModalArrayList.get(currentPos).getOption2());
                option3Btn.setText(quizModalArrayList.get(currentPos).getOption3());
                option4Btn.setText(quizModalArrayList.get(currentPos).getOption4());
            }

        }

        // Add questions from the data array to the quiz array
        private void getQuizQuestion(ArrayList<QuizModal> quizModalArrayList){
            // Generate 20 random questions and add them to the list
            for (int i = 0; i < 20; i++) {
                quizModalArrayList.add(QuizModal.generateRandomQuestion());
            }
        }
}

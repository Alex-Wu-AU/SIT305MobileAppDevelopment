package com.example.learningexperience;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView questionTV, questionNumberTV;
    private Button option1Btn, option2Btn, option3Btn, option4Btn, submitBtn;
    private ArrayList<QuizModal> quizModalArrayList;
    private Random random;
    private int currentScore = 0, questionAttempted = 1, currentPos;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        sharedPreferences = getSharedPreferences("QuizData", Context.MODE_PRIVATE);
        // Fetch quiz questions from API
        fetchQuizQuestions();

        // Click on the submit button to move on
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update quiz data in SharedPreferences
                updateQuizData();
                questionAttempted++;
                if (questionAttempted <= 5) {
                    currentPos = random.nextInt(quizModalArrayList.size());
                    setDataToViews(currentPos);
                } else {
                    showBottomSheet();
                }

            }

        });

        // Button click behavior for options
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

        Button btnGoToProfileActivity = findViewById(R.id.btnGoToProfileActivity);
        btnGoToProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("currentScore", currentScore);
                intent.putExtra("questionAttempted", questionAttempted);
                startActivity(intent);
            }
        });
    }

    private void fetchQuizQuestions() {
        QuizApiService service = RetrofitClientInstance.getRetrofitInstance().create(QuizApiService.class);
        Call<QuizResponse> call = service.getQuizQuestions(5);

        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QuizQuestion> questions = response.body().getResults();
                    for (QuizQuestion q : questions) {
                        List<String> options = new ArrayList<>(q.getIncorrectAnswers());
                        options.add(q.getCorrectAnswer());
                        Collections.shuffle(options);
                        quizModalArrayList.add(new QuizModal(q.getQuestion(), options.get(0), options.get(1), options.get(2), options.get(3), q.getCorrectAnswer()));
                    }
                    currentPos = random.nextInt(quizModalArrayList.size());
                    setDataToViews(currentPos);
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                // Handle the error
            }
        });
    }

    private void resetButtonColors() {
        option1Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option2Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option3Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
        option4Btn.setBackgroundColor(Color.parseColor("#6067A9")); // Navy
    }

    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout) findViewById(R.id.idLLScore));
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

    private void setDataToViews(int currentPos) {
        questionNumberTV.setText("Question Attempted: " + questionAttempted + "/5");

        if (questionAttempted > 5) {
            showBottomSheet();
        } else {
            QuizModal quizModal = quizModalArrayList.get(currentPos);
            questionTV.setText(quizModal.getQuestion());
            option1Btn.setText(quizModal.getOption1());
            option2Btn.setText(quizModal.getOption2());
            option3Btn.setText(quizModal.getOption3());
            option4Btn.setText(quizModal.getOption4());
        }
    }

    private void updateQuizData() {
        // Update quiz data in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("currentScore", currentScore);
        editor.putInt("questionAttempted", questionAttempted);
        editor.apply();
    }
}

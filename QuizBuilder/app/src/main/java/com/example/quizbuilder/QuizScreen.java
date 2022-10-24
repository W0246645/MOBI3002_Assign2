package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizScreen extends AppCompatActivity {
    Button btn_quit, btn_restart, btn_submit, btn_term1, btn_term2, btn_term3, btn_term4;
    TextView txt_def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);

        btn_quit = findViewById(R.id.btn_quit);
        btn_restart = findViewById(R.id.btn_restart);
        btn_submit = findViewById(R.id.btn_submit);
        btn_term1 = findViewById(R.id.btn_term1);
        btn_term2 = findViewById(R.id.btn_term2);
        btn_term3 = findViewById(R.id.btn_term3);
        btn_term4 = findViewById(R.id.btn_term4);

        txt_def = findViewById(R.id.txt_definition);

        btn_quit.setOnClickListener(onQuitClicked);

        btn_term1.setOnClickListener(onTermClicked);
        btn_term2.setOnClickListener(onTermClicked);
        btn_term3.setOnClickListener(onTermClicked);
        btn_term4.setOnClickListener(onTermClicked);
    }

    public View.OnClickListener onQuitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(QuizScreen.this, MainActivity.class);
            i.putExtra("name", getIntent().getStringExtra("name"));
            i.putExtra("highscores", getIntent().getStringExtra("highscores"));
            startActivity(i);
        }
    };

    public View.OnClickListener onTermClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setBackground(getDrawable(R.drawable.rounded_corners_selected));
        }
    };
}
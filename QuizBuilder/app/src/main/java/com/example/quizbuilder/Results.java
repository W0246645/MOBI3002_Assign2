package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Results extends AppCompatActivity {
    TextView txt_score;
    Button btn_highscores;
    Intent intent;
    String name;
    ArrayList<ArrayList<String>> highscores;
    int numCorrect, numTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        intent = getIntent();

        name = intent.getStringExtra("name");
        highscores = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("highscores");
        numCorrect = intent.getIntExtra("numCorrect", 0);
        numTerms = intent.getIntExtra("numTerms", 0);

        txt_score = findViewById(R.id.txt_score);
        btn_highscores = findViewById(R.id.btn_highscores);

        btn_highscores.setOnClickListener(onHighscoresClicked);

        String score = numCorrect + "/" + numTerms;
        txt_score.setText(score);

        ArrayList<String> scoreData = new ArrayList<>();
        scoreData.add(name);
        scoreData.add(String.valueOf(numCorrect));
        scoreData.add(String.valueOf(numTerms));

        highscores.add(scoreData);
        highscores.sort(new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {

                return o1.get(1).compareTo(o2.get(1));
            }
        });
        Collections.reverse(highscores);
    }

    public View.OnClickListener onHighscoresClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Results.this, MainActivity.class);
            i.putExtra("name", name);
            i.putExtra("highscores", highscores);
            startActivity(i);
        }
    };
}
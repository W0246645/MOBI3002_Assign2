package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class QuizScreen extends AppCompatActivity {
    Button btn_quit, btn_restart, btn_submit, btn_term1, btn_term2, btn_term3, btn_term4;
    Button[] termButtons;
    TextView txt_def, txt_termNum;
    Intent intent;
    ArrayList<String> defs, terms, termsToChoose;
    HashMap<String, String> map;
    String def;
    Button selectedButton;
    int numCorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);

        intent = getIntent();

        defs = intent.getStringArrayListExtra("defs");
        terms = intent.getStringArrayListExtra("terms");
        map = (HashMap<String, String>) intent.getSerializableExtra("map");
        termsToChoose = new ArrayList<>();
        numCorrect = intent.getIntExtra("numCorrect", 0);

        btn_quit = findViewById(R.id.btn_quit);
        btn_restart = findViewById(R.id.btn_restart);
        btn_submit = findViewById(R.id.btn_submit);
        btn_term1 = findViewById(R.id.btn_term1);
        btn_term2 = findViewById(R.id.btn_term2);
        btn_term3 = findViewById(R.id.btn_term3);
        btn_term4 = findViewById(R.id.btn_term4);

        txt_def = findViewById(R.id.txt_definition);
        txt_termNum = findViewById(R.id.txt_termNum);

        btn_quit.setOnClickListener(onQuitClicked);
        btn_submit.setOnClickListener(onSubmitClicked);

        termButtons = new Button[]{btn_term1, btn_term2, btn_term3, btn_term4};
        for (Button btn: termButtons) {
            btn.setOnClickListener(onTermClicked);
        }

        Collections.shuffle(defs);
        Collections.shuffle(terms);
        def = defs.get(0);
        defs.remove(def);
        txt_def.setText(def);

        termsToChoose.add(map.get(def));
        int counter = 0;
        while (termsToChoose.size() != 4) {
            if (!termsToChoose.contains(terms.get(counter))) {
                termsToChoose.add(terms.get(counter));
            }
            counter++;
        }

        Collections.shuffle(termsToChoose);

        for (int i = 0; i < 4; i++) {
            termButtons[i].setText(termsToChoose.get(i));
        }
        btn_term1.setText(termsToChoose.get(0));
        btn_term2.setText(termsToChoose.get(1));
        btn_term3.setText(termsToChoose.get(2));
        btn_term4.setText(termsToChoose.get(3));

        String termNum = String.format("Term\n%d/%d", (map.size() - defs.size()) ,map.size());
        txt_termNum.setText(termNum);
    }

    public View.OnClickListener onQuitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(QuizScreen.this, MainActivity.class);
            i.putExtra("name", getIntent().getStringExtra("name"));
            i.putExtra("highscores", getIntent().getParcelableArrayListExtra("highscores"));
            startActivity(i);
        }
    };

    public View.OnClickListener onTermClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (Button btn: termButtons) {
                btn.setBackgroundResource(R.drawable.rounded_corners);
            }

            view.setBackgroundResource(R.drawable.rounded_corners_selected);
            selectedButton = (Button) view;
        }
    };

    public View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedButton == null) {
                Animation shake = AnimationUtils.loadAnimation(QuizScreen.this, R.anim.shake);
                for (Button btn: termButtons) {
                    btn.startAnimation(shake);
                }
                return;
            } else if (selectedButton.getText() == map.get(def)) {
                selectedButton.setBackgroundResource(R.drawable.rounded_corners_green);
                numCorrect ++;
            } else {
                selectedButton.setBackgroundResource(R.drawable.rounded_corners_incorrect);
                for (Button btn: termButtons) {
                    if (btn.getText() == map.get(def)) {
                        btn.setBackgroundResource(R.drawable.rounded_corners_green_border);
                    }
                }
            }
            if (defs.size() != 0) {
                btn_submit.setText(R.string.btn_next);
                btn_submit.setOnClickListener(onNextClicked);
            } else {
                btn_submit.setText(R.string.btn_results);
                btn_submit.setOnClickListener(onResultsClicked);
            }
            for (Button btn : termButtons) {
                btn.setEnabled(false);
            }
        }
    };

    public View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(QuizScreen.this, QuizScreen.class);
            i.putExtra("name", intent.getStringExtra("name"));
            i.putExtra("defs", defs);
            i.putExtra("terms", terms);
            i.putExtra("map", map);
            i.putExtra("highscores", intent.getSerializableExtra("highscores"));
            i.putExtra("numCorrect", numCorrect);
            startActivity(i);
        }
    };

    public View.OnClickListener onResultsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(QuizScreen.this, Results.class);
            i.putExtra("name", intent.getStringExtra("name"));
            i.putExtra("highscores", intent.getSerializableExtra("highscores"));
            i.putExtra("numCorrect", numCorrect);
            i.putExtra("numTerms", map.size());
            startActivity(i);
        }
    };
}
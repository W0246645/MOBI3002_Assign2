package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btn_start;
    TextView txt_highscores;
    EditText editText_name;
    Intent intent;

    ArrayList<ArrayList<String>> highscores;
    String name;
    ArrayList<String> defs = new ArrayList<>(Arrays.asList("d1", "d2", "d3", "d4", "d5"));
    ArrayList<String> terms = new ArrayList<>(Arrays.asList("t1", "t2", "t3", "t4", "t5"));
    HashMap<String, String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        if (intent != null) {
            highscores = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("highscores");
            name = intent.getStringExtra("name");
        }

        btn_start = findViewById(R.id.btn_highscores);
        txt_highscores = findViewById(R.id.txt_score);
        editText_name = findViewById(R.id.editText_name);

        btn_start.setOnClickListener(onSubmitClicked);
        String highscoresTxt = "";
        if (highscores != null) {
            for (int i = 0; i < highscores.size() && i != 10; i++) {
                ArrayList<String> score = highscores.get(i);
                highscoresTxt += (i + 1) + ". " + score.get(0) + " - " + score.get(1) + "/" + score.get(2) + "\n";
            }
        } else {
            highscores = new ArrayList<>();
        }
        txt_highscores.setText(highscoresTxt);
        editText_name.setText(name);

        for (int i = 0; i < defs.size(); i++) {
            map.put(defs.get(i), terms.get(i));
        }
    }

    public View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (editText_name.getText().toString().trim().length() > 0) {
                Intent i = new Intent(MainActivity.this, QuizScreen.class);
                i.putExtra("name", editText_name.getText().toString());
                i.putExtra("defs", defs);
                i.putExtra("terms", terms);
                i.putExtra("map", map);
                i.putExtra("highscores", highscores);
                startActivity(i);
            } else {
                Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                editText_name.startAnimation(shake);
            }
        }
    };
}
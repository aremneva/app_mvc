package com.example.mvc1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;

    private Button btnT;
    private  Button btnF;
    private Button btnN;
    private TextView textQ;
    private ImageView imgQ;
    private EditText edQ;
    private TextView correctQ;
    private TextView maxQ;
    private TextView correctmax;

    ArrayList<String> answers_user = new ArrayList<String>();

    private Question[] questions = new Question[]{
      new Question("Москва - столица России", "true",R.drawable.question),
      new Question("5 умножить на 5 = 35","false",R.drawable.math),
      new Question("Вода может быть в трех разных состояниях", "true",R.drawable.question),
            new Question("3x7=","21",R.drawable.math),
            new Question("689-11=","678",R.drawable.math),
            new Question("Год основания Санкт-Петербурга", "1703",R.drawable.question)
    };
    private  int CurrentIndex = 0;
    private int corr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnT = (Button) findViewById(R.id.true_btn);
        btnF = (Button) findViewById(R.id.false_btn);
        btnN = (Button) findViewById(R.id.next_btn);
        textQ = (TextView) findViewById(R.id.quest_text);
        imgQ=(ImageView) findViewById(R.id.imgQ);
        edQ= (EditText) findViewById(R.id.answer);
        correctQ=(TextView) findViewById(R.id.correct);
        maxQ=(TextView) findViewById(R.id.max);
        correctmax=(TextView) findViewById(R.id.correctMax);





        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("true");
                CurrentIndex=(CurrentIndex+1)%questions.length;
                updateQuestion();
            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("false");
                CurrentIndex=(CurrentIndex+1)%questions.length;
                updateQuestion();
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer =edQ.getText().toString();
                Log.d(MyConstants.LOG_TAG,"User answer: "+answer);

                if(!answer.equals(" ")){
                checkAnswer(answer);
                }
                CurrentIndex=(CurrentIndex+1)%questions.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    private  void updateQuestion(){ //изменение вопроса
        String questionId = questions[CurrentIndex].getTextId(); // получение вопроса по id
        int path = questions[CurrentIndex].getPathImg();
        textQ.setText(questionId);
        imgQ.setImageResource(path);
    }
    private void checkAnswer(String userAnswer){ //проверка ответа
        correctmax.setText("");
        maxQ.setText("");
        sPref = getPreferences(MODE_PRIVATE);
        int getPref = sPref.getInt(MyConstants.MAX_CORRECT,0);
        String answer = questions[CurrentIndex].isAnswer();
        Log.d(MyConstants.LOG_TAG,"Correct answer: "+answer);
        SharedPreferences.Editor ed = sPref.edit();

        if (userAnswer.equals(answer)){
            Toast.makeText(MainActivity.this,R.string.resultT, Toast.LENGTH_SHORT).show();
            corr++;
            correctQ.setText("Correct: "+corr);
            answers_user.add(answer);
        }
        else{
            Toast.makeText(MainActivity.this,R.string.resultF, Toast.LENGTH_SHORT).show();
            if (getPref<corr){
                ed.putInt(MyConstants.MAX_CORRECT,corr);     //ключ и значение
                ed.apply();
                ed.putString(MyConstants.CORRECT_ANSWER, String.valueOf(answers_user));
                ed.apply();
                Log.d(MyConstants.LOG_TAG, "Correct answers: "+ String.valueOf(answers_user));
                /*Toast toast = Toast.makeText(this, "Верные ответы: "+String.valueOf(answers_user), Toast.LENGTH_LONG);
                toast.show();*/
                correctmax.setText("Верные ответы: "+String.valueOf(answers_user));
                answers_user.clear();
            }
            else{
                answers_user.clear();
            }
            corr=0;
            correctQ.setText("Correct: "+corr);
            sPref = getPreferences(MODE_PRIVATE);
            int getP = sPref.getInt(MyConstants.MAX_CORRECT,0);
            maxQ.setText("Max correct: "+ getP);
        }
    }
}
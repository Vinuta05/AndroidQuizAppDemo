package com.android.quizappdemo.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.quizappdemo.R;
import com.android.quizappdemo.appController.QuizDemoApplication;
import com.android.quizappdemo.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsFragment extends Fragment{

    private TextView txt_question;
    private RadioButton radio_btn1;
    private RadioButton radio_btn2;
    private RadioButton radio_btn3;
    private RadioButton radio_btn4;
    private RadioGroup radioGroup;
    private Button btn_next;

    private List<Questions> questionsList;
    private int presentQuestion = 0;
    private int correctAnswer =0 , wrongAnswer = 0;
    private int getCorrectAnswerCount , getWrongAnswerCount;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.questions_layout, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        loadQuestionList();
        setQuestion(presentQuestion);
        Collections.shuffle(questionsList);
        if(presentQuestion<questionsList.size()-1){
            presentQuestion++;
            setQuestion(presentQuestion);

        }
    }

    private void initViews(){
        txt_question = mView.findViewById(R.id.txt_question);
        radio_btn1 = mView.findViewById(R.id.radio_btn1);
        radio_btn2 = mView.findViewById(R.id.radio_btn2);
        radio_btn3= mView.findViewById(R.id.radio_btn3);
        radio_btn4 = mView.findViewById(R.id.radio_btn4);
        radio_btn4 = mView.findViewById(R.id.radio_btn4);
        btn_next = mView.findViewById(R.id.btn_next);
        radioGroup = mView.findViewById(R.id.radio_grp);
        radio_btn1.setOnClickListener(radioButtonClick);
        radio_btn2.setOnClickListener(radioButtonClick);
        radio_btn3.setOnClickListener(radioButtonClick);
        radio_btn4.setOnClickListener(radioButtonClick);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(presentQuestion<questionsList.size()-1){
                    presentQuestion++;
                    setQuestion(presentQuestion);
                    radioGroup.clearCheck();
                }else {
                    FragmentManager fm = getChildFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ResultFragment resultFragment = new ResultFragment();
                    Bundle b = new Bundle();
                    b.putInt("correct",getCorrectAnswerCount);
                    b.putInt("wrong",getWrongAnswerCount);
                    b.putInt("numberOfQuestions",questionsList.size()-1);
                    resultFragment.setArguments(b);
                    ft.add(R.id.frame_id_for_result , resultFragment);
                    ft.commit();

                }
            }
        });
    }
    private void loadQuestionList(){


        questionsList = new ArrayList<>();
        String jsonString = loadJSONFileFromAsset("questions.json");
        try {
            if(null!=jsonString){
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for(int i=0;i<= questions.length();i++) {
                JSONObject object = questions.getJSONObject(i);
                String questionNumber = object.getString("question");
                String option1 = object.getString("option1");
                String option2 = object.getString("option2");
                String option3 = object.getString("option3");
                String option4 = object.getString("option4");
                String correctAnswer = object.getString("correct");

                questionsList.add(new Questions(questionNumber, option1, option2, option3, option4, correctAnswer));
            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void setQuestion(int number){
        txt_question.setText(questionsList.get(number).getQuestions());
        radio_btn1.setText(questionsList.get(number).getOption1());
        radio_btn2.setText(questionsList.get(number).getOption2());
        radio_btn3.setText(questionsList.get(number).getOption3());
        radio_btn4.setText(questionsList.get(number).getOption4());


    }

    private String loadJSONFileFromAsset(String file) {
        String json = null;
        try {
            AssetManager assetManager = QuizDemoApplication.getInstance().getAssets();
            InputStream is = assetManager.open(file);
             int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private View.OnClickListener radioButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.radio_btn1:
                    if(questionsList.get(presentQuestion).getOption1().equals(questionsList.get(presentQuestion).getCorrectAnswer())){
                        showAlertDialog(getString(R.string.txt_correct),R.drawable.ic_correct);
                        correctAnswer++;
                        getCorrectAnswerCount = correctAnswer;
                    }else {
                        showAlertDialog(getString(R.string.txt_wrong),R.drawable.ic_wrong);
                        wrongAnswer++;
                        getWrongAnswerCount = wrongAnswer;
                    }
                    presentQuestion++;
                    break;
                case R.id.radio_btn2:
                    if(questionsList.get(presentQuestion).getOption2().equals(questionsList.get(presentQuestion).getCorrectAnswer())){
                        showAlertDialog(getString(R.string.txt_correct),R.drawable.ic_correct);
                        correctAnswer++;
                        getCorrectAnswerCount = correctAnswer;

                    }else {

                        showAlertDialog(getString(R.string.txt_wrong),R.drawable.ic_wrong);
                        wrongAnswer++;
                        getWrongAnswerCount = wrongAnswer;
                    }

                    break;
                case R.id.radio_btn3:
                    if(questionsList.get(presentQuestion).getOption3().equals(questionsList.get(presentQuestion).getCorrectAnswer())){

                        showAlertDialog(getString(R.string.txt_correct),R.drawable.ic_correct);
                        correctAnswer++;
                        getCorrectAnswerCount = correctAnswer;
                    }else {
                        showAlertDialog(getString(R.string.txt_wrong),R.drawable.ic_wrong);
                        wrongAnswer++;
                        getWrongAnswerCount = wrongAnswer;}
                    break;
                case R.id.radio_btn4:
                    if(questionsList.get(presentQuestion).getOption4().equals(questionsList.get(presentQuestion).getCorrectAnswer())){
                        correctAnswer++;
                        getCorrectAnswerCount = correctAnswer;
                        showAlertDialog(getString(R.string.txt_correct),R.drawable.ic_correct);
                    }else {
                        showAlertDialog(getString(R.string.txt_wrong),R.drawable.ic_wrong);
                        wrongAnswer++;
                        getWrongAnswerCount = wrongAnswer;

                    }
                break;
                default:
                    break;
            }
        }
    };

    private void showAlertDialog(String message , int imageView){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(message);
        dialog.setIcon(imageView);
        dialog.setPositiveButton(getString(R.string.dialog_txt_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }
}
